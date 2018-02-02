package thepiratebayapi.services;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import thepiratebayapi.beans.TPBayTorrent;
import thepiratebayapi.beans.TPBayTorrentResultList;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Romain on 01/02/2018.
 */
@SuppressWarnings("deprecation")
public class TPBayAPITest {
    TPBayAPI tpBayAPI;

    @Before
    public void before() {
        tpBayAPI = new TPBayAPI("https://ukpirate.click");
    }

	@SuppressWarnings("unused")
	@Test
    public void searchTorrents() {
        Map<String, Serializable> params = new HashMap<String, Serializable>();
        params.put("page", "0");
        TPBayTorrentResultList torrentResultList = tpBayAPI.searchTorrents("Thor", params);
        TPBayTorrent torrent0 = tpBayAPI.getTorrent(torrentResultList.get(0).getUrl());
        TPBayTorrent torrent1 = tpBayAPI.getTorrent(torrentResultList.get(1));
        
        Assert.assertNotNull(torrentResultList);
    }
}
