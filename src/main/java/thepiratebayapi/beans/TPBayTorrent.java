package thepiratebayapi.beans;

/**
 * Created by Romain on 01/02/2018.
 */
public class TPBayTorrent extends TPBayTorrentResult {
    private String magnet;

    public TPBayTorrent(TPBayPage page) {

    }

    public String getMagnet() {
        return magnet;
    }
}
