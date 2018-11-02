package thepiratebayapi.exceptions;

/**
 * Created by Romain on 02/11/2018.
 */
public class TPBayTorrentSearchException extends TPBayAPIException {
    public TPBayTorrentSearchException(String query) {
        super("Error searching for torrents : " + query);
    }
}
