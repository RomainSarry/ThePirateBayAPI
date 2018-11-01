package thepiratebayapi.beans;

/**
 * Created by Romain on 01/02/2018.
 */
public class TPBayTorrent {
    protected String description;

    protected Integer leechers;
    
    protected String magnet;

    protected String name;

    protected Integer seeders;

    protected String url;

    protected String user;

    public String getDescription() {
        return description;
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
