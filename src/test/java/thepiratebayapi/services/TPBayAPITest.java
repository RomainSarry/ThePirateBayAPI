package thepiratebayapi.services;

import org.junit.Before;
import org.junit.Test;
import thepiratebayapi.beans.TPBayTorrentResultList;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Romain on 01/02/2018.
 */
public class TPBayAPITest {
    TPBayAPI tpBayAPI;

    @Before
    public void before() {
        tpBayAPI = new TPBayAPI("https://ukpirate.click");
    }

    @Test
    public void searchTorrents() {
        Map<String, Serializable> params = new HashMap<String, Serializable>();
        params.put("page", "0");
        TPBayTorrentResultList torrentResultList = tpBayAPI.searchTorrents("Thor", params);
    }
}
