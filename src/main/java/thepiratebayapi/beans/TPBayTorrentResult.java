package thepiratebayapi.beans;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Romain on 01/02/2018.
 */
public class TPBayTorrentResult {
    private String description;

    private Integer leechers;

    private String name;

    private Integer seeders;

    private String url;

    private String user;

    protected TPBayTorrentResult() {

    }

    public TPBayTorrentResult(Element el) {
        name = el.select(".detName").first().text();
        description = el.select("font.detDesc").first().text();
        Elements userElements = el.select("a.detDesc");
        if (userElements != null && !userElements.isEmpty()) {
            user = userElements.first().text();
        }
        url = el.select(".detName a").first().attr("href");
        seeders = Integer.valueOf(el.select("td").get(2).text());
        leechers = Integer.valueOf(el.select("td").get(3).text());
    }

    public String getDescription() {
        return description;
    }

    public Integer getLeechers() {
        return leechers;
    }

    public String getName() {
        return name;
    }

    public Integer getSeeders() {
        return seeders;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }
}
