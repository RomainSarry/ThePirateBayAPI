package thepiratebayapi.exceptions;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Romain on 02/11/2018.
 */
public class TPBayParametersException extends TPBayAPIException {
    public TPBayParametersException(Map<String, Serializable> parameters) {
        super("Error parsing parameters : " + parameters.toString());
    }
}
