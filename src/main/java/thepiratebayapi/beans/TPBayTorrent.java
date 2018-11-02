package thepiratebayapi.beans;

import io.dropwizard.util.Size;

import java.util.Date;

/**
 * Created by Romain on 01/02/2018.
 */
public class TPBayTorrent {

    public static final String TORRENT_NAME_SELECTOR = "#title";

    public static final String TORRENT_MAGNET_SELECTOR = ".download a";

    public static final String TORRENT_INFOS_SELECTOR = "#details";

    public static final String TORRENT_INFOS_KEY_SELECTOR = "dl dt";

    public static final String TORRENT_INFOS_VALUE_SELECTOR = "dl dd";

    public static final String TORRENT_DESCRIPTION_SELECTOR = ".nfo";

    private Integer leechers;

    private String magnet;

    private String name;

    private String nfo;

    private Integer seeders;

    private Size size;

    private String sizeString;

    private Date uploaded;

    private String url;

    private String user;

    public TPBayTorrent(String name, Date uploaded, String sizeString, String user, String url, Integer seeders, Integer leechers) {
        this.name = name;
        this.uploaded = uploaded;
        this.sizeString = sizeString;
        this.user = user;
        this.url = url;
        this.seeders = seeders;
        this.leechers = leechers;
    }

    public TPBayTorrent(String name, Date uploaded, String sizeString, Size size, String user, String url, Integer seeders, Integer leechers, String magnet, String nfo) {
        this(name, uploaded, sizeString, user, url, seeders, leechers);
        this.size = size;
        this.magnet = magnet;
        this.nfo = nfo;
    }

    public Integer getLeechers() {
        return leechers;
    }

    public String getMagnet() {
        return magnet;
    }

    public String getName() {
        return name;
    }

    public String getNfo() {
        return nfo;
    }

    public Integer getSeeders() {
        return seeders;
    }

    public Size getSize() {
        return size;
    }

    public String getSizeString() {
        return sizeString;
    }

    public Date getUploaded() {
        return uploaded;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }
}
