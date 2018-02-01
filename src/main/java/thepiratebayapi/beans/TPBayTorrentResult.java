package thepiratebayapi.beans;

/**
 * Created by Romain on 01/02/2018.
 */
public class TPBayTorrentResult {
    private Integer leechers;

    private String name;

    private Integer seeders;

    private String size;

    private String url;

    private String user;

    public Integer getLeechers() {
        return leechers;
    }

    public String getName() {
        return name;
    }

    public Integer getSeeders() {
        return seeders;
    }

    public String getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }
}
