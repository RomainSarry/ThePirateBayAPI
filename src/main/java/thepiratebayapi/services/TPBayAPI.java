package thepiratebayapi.services;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import thepiratebayapi.beans.TPBayPage;
import thepiratebayapi.beans.TPBayTorrent;
import thepiratebayapi.beans.TPBayTorrentResult;
import thepiratebayapi.beans.TPBayTorrentResultList;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Romain on 01/02/2018.
 */
public class TPBayAPI {
	private String urlBase;
	
    private String urlSearch;

    public TPBayAPI(String urlBase) {
    	this.urlBase = urlBase;
        urlSearch = urlBase + "/s/";
    }

    private static TPBayPage getPageByUrl(String url) {
        Document htmlDom = null;

        try {
            System.out.println("Fetching URL : " + url);
            htmlDom = Jsoup.connect(url).get();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new TPBayPage(htmlDom);
    }

    public TPBayTorrentResultList searchTorrents(String query, Map<String, Serializable> parameters) {
        parameters.put("q", query);
        return new TPBayTorrentResultList(getPageByUrl(urlSearch + "?" + getParamsAsString(parameters)));
    }
    
    public TPBayTorrent getTorrent(String url) {
    	return new TPBayTorrent(getPageByUrl(url));
    }
    
    public TPBayTorrent getTorrent(TPBayTorrentResult torrentResult) {
    	return getTorrent(torrentResult.getUrl());
    }

    private String getParamsAsString(Map<String, Serializable> parameters) {
        return URLEncodedUtils.format(getParamsAsNameValuePairs(parameters), "utf-8");
    }

    private List<NameValuePair> getParamsAsNameValuePairs(Map<String, Serializable> parameters) {
        List<NameValuePair> params = new LinkedList<NameValuePair>();
        if (parameters != null && !parameters.isEmpty()) {
            for (Map.Entry<String, Serializable> entry : parameters.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));
            }
        }
        return params;
    }
}
