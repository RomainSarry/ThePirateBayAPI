package thepiratebayapi.services;

import io.dropwizard.util.Size;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import thepiratebayapi.beans.TPBayPage;
import thepiratebayapi.beans.TPBaySearchResults;
import thepiratebayapi.beans.TPBayTorrent;
import thepiratebayapi.exceptions.TPBayParametersException;
import thepiratebayapi.exceptions.TPBayTorrentFetchingException;
import thepiratebayapi.exceptions.TPBayTorrentSearchException;
import thepiratebayapi.exceptions.TPBayURLException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Romain on 01/02/2018.
 */
public class TPBayAPI {

    private String urlString;

    private final SimpleDateFormat searchFormatter = new SimpleDateFormat("'Uploaded' MM-dd-yyyy");

    private final SimpleDateFormat torrentFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

    public TPBayAPI(String urlString) throws TPBayURLException {
    	this.urlString = urlString;
    	getPage(urlString);
    }

    private TPBayPage getPage(String url) throws TPBayURLException {
        Document htmlDom = null;

        try {
            Connection connection = Jsoup.connect(url).followRedirects(false);
            htmlDom = connection.get();
            if (connection.response().statusCode() != 200) {
                throw new TPBayURLException(url);
            }
        } catch (Exception e) {
            throw new TPBayURLException(url);
        }

        return new TPBayPage(htmlDom);
    }

    public TPBaySearchResults searchTorrents(String query, Map<String, Serializable> parameters) throws TPBayURLException, TPBayParametersException, TPBayTorrentSearchException {
        try {
            TPBaySearchResults torrents = new TPBaySearchResults();

            if (parameters == null) {
                parameters = new HashMap<>();
            }
            parameters.put("q", query);

            TPBayPage page = getPage(urlString + "/s/?" + getParamsAsString(parameters));

            for (Element torrentContent : page.getFields(TPBaySearchResults.TORRENT_RESULTS_SELECTOR)) {
                String name = torrentContent.select(TPBaySearchResults.TORRENT_NAME_SELECTOR).first().text();
                String[] description = torrentContent.select(TPBaySearchResults.TORRENT_DESCRIPTION_SELECTOR).first().text().split(", ");
                Date uploaded;
                if (description[0].contains("Today"))  {
                    uploaded = new Date();
                } else if (description[0].contains("Y-day")) {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -1);
                    uploaded = cal.getTime();
                } else {
                    uploaded = searchFormatter.parse(description[0].replaceAll("\u00a0", "-"));
                }
                String sizeString = description[1].split("\\s+")[1].replace("\u00a0", " ");

                Elements userElements = torrentContent.select(TPBaySearchResults.TORRENT_USER_SELECTOR);
                String user = null;
                if (userElements != null && !userElements.isEmpty()) {
                    user = userElements.first().text();
                }

                String url = torrentContent.select(TPBaySearchResults.TORRENT_URL_SELECTOR).first().absUrl("href");
                Integer seeders = Integer.valueOf(torrentContent.select(TPBaySearchResults.TORRENT_PEERS_SELECTOR).get(2).text());
                Integer leechers = Integer.valueOf(torrentContent.select(TPBaySearchResults.TORRENT_PEERS_SELECTOR).get(3).text());

                torrents.add(new TPBayTorrent(name, uploaded, sizeString, user, url ,seeders, leechers));
            }

            return torrents;
        } catch (TPBayURLException | TPBayParametersException e) {
            throw e;
        } catch (Exception e) {
            throw new TPBayTorrentSearchException(query);
        }
    }
    
    public TPBayTorrent getTorrent(String urlString) throws TPBayURLException, TPBayTorrentFetchingException {
        try {
            TPBayPage page = getPage(urlString);

            String name = page.getField(TPBayTorrent.TORRENT_NAME_SELECTOR).text();
            String url = page.getHtmlDom().location();

            Map<String, String> infosMap = new HashMap<>();
            Element infos = page.getField(TPBayTorrent.TORRENT_INFOS_SELECTOR);
            Elements infosKeys = infos.select(TPBayTorrent.TORRENT_INFOS_KEY_SELECTOR);
            Elements infosValues = infos.select(TPBayTorrent.TORRENT_INFOS_VALUE_SELECTOR);
            for (int i = 0; i < infosKeys.size(); i++) {
                String infosKey = infosKeys.get(i).text();
                infosKey = infosKey.substring(0, infosKey.length() - 1).toLowerCase();
                String infosValue = infosValues.get(i).text();
                infosMap.put(infosKey, infosValue);
            }

            Date uploaded = null;
            if (infosMap.containsKey("uploaded")) {
                uploaded = torrentFormatter.parse(infosMap.get("uploaded"));
            }
            Size size = null;
            String sizeString = null;
            if (infosMap.containsKey("size")) {
                String[] sizeParts = infosMap.get("size").replaceAll("\\u00a0", " ").split("\\s\\(");
                sizeString = sizeParts[0];
                String s = sizeParts[1];
                s = s.substring(0, s.length() - 1).toLowerCase();
                size = Size.parse(s);
            }
            String user = null;
            if (infosMap.containsKey("by")) {
                user = infosMap.get("by");
            }
            Integer seeders = null;
            if (infosMap.containsKey("seeders")) {
                seeders = Integer.valueOf(infosMap.get("seeders"));
            }
            Integer leechers = null;
            if (infosMap.containsKey("leechers")) {
                leechers = Integer.valueOf(infosMap.get("leechers"));
            }

            String magnet = page.getField(TPBayTorrent.TORRENT_MAGNET_SELECTOR).attr("href");
            String nfo = page.getField(TPBayTorrent.TORRENT_DESCRIPTION_SELECTOR).text();

            return new TPBayTorrent(name, uploaded, sizeString, size, user, url, seeders, leechers, magnet, nfo);
        } catch (TPBayURLException e) {
            throw e;
        } catch (Exception e) {
            throw new TPBayTorrentFetchingException(urlString);
        }
    }

    private String getParamsAsString(Map<String, Serializable> parameters) throws TPBayParametersException {
        try {
            List<NameValuePair> paramsAsNameValuePairs = new LinkedList<NameValuePair>();
            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String, Serializable> entry : parameters.entrySet()) {
                    paramsAsNameValuePairs.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));
                }
            }

            return URLEncodedUtils.format(paramsAsNameValuePairs, "utf-8");
        } catch (Exception e) {
            throw new TPBayParametersException(parameters);
        }
    }
}
