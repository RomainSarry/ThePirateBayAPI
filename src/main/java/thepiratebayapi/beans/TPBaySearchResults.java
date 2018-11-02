package thepiratebayapi.beans;

import java.util.ArrayList;

/**
 * Created by Romain on 02/11/2018.
 */
public class TPBaySearchResults extends ArrayList<TPBayTorrent> {
    public static final String TORRENT_RESULTS_SELECTOR = "#searchResult tbody tr";

    public static final String TORRENT_NAME_SELECTOR = ".detName";

    public static final String TORRENT_DESCRIPTION_SELECTOR = "font.detDesc";

    public static final String TORRENT_USER_SELECTOR = "a.detDesc";

    public static final String TORRENT_URL_SELECTOR = ".detName a";

    public static final String TORRENT_PEERS_SELECTOR = "td";
}
