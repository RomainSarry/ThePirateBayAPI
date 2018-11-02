package thepiratebayapi.exceptions;

/**
 * Created by Romain on 02/11/2018.
 */
public class TPBayTorrentFetchingException extends TPBayAPIException {
    public TPBayTorrentFetchingException(String urlString) {
        super("Error fetching torrent : " + urlString);
    }
}
