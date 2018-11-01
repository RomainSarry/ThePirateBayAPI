package thepiratebayapi.services;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import thepiratebayapi.beans.TPBayPage;
import thepiratebayapi.beans.TPBayTorrent;
import thepiratebayapi.beans.TPBayTorrentResultList;
import thepiratebayapi.exceptions.TPBayURLException;

/**
 * Created by Romain on 01/02/2018.
 */
public class TPBayAPI {
	
	private String urlString;

    public TPBayAPI(String urlString) {
    	this.urlString = urlString;
    }

    private static TPBayPage getPageByUrl(String url) throws TPBayURLException {
        Document htmlDom = null;

        try {
            htmlDom = Jsoup.connect(url).get();
        }
        catch (Exception e) {
            throw new TPBayURLException(url);
        }

        return new TPBayPage(htmlDom);
    }

    public List<TPBayTorrent> searchTorrents(String query, Map<String, Serializable> parameters) throws TPBayURLException {
        parameters.put("q", query);
        TPBayPage page = getPageByUrl(urlString + "/s/?" + getParamsAsString(parameters));
    }
    
    public TPBayTorrent getTorrent(String url) {
    	return new TPBayTorrent(getPageByUrl(url));
    }
    
    public TPBayTorrent getTorrent(TPBayTorrent torrentResult) {
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
