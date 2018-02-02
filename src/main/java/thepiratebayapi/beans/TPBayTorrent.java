package thepiratebayapi.beans;

import org.jsoup.select.Elements;

/**
 * Created by Romain on 01/02/2018.
 */
public class TPBayTorrent extends TPBayTorrentResult {
    private String magnet;

    public TPBayTorrent(TPBayPage page) {
    	name = page.getField("#title").text();
    	description = page.getField(".nfo").text();
        Elements userElements = page.getFields(".col2 dd a");
        if (userElements != null && !userElements.isEmpty()) {
            user = userElements.first().text();
        }
        url = page.htmlDom.location();
        Elements dds = page.getFields(".col2 dd");
        seeders = Integer.valueOf(dds.get(2).text());
        leechers = Integer.valueOf(dds.get(3).text());
        magnet = page.getField(".download a").attr("href");
    }

    public String getMagnet() {
        return magnet;
    }
}
