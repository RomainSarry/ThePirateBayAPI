package thepiratebayapi.exceptions;

/**
 * Created by Romain on 28/10/2018.
 */
public class TPBayURLException extends TPBayAPIException {

	private static final long serialVersionUID = 1L;

    public TPBayURLException(String urlString) {
        super("Cannot connect to ThePirateBay with URL : " + urlString);
    }
}
