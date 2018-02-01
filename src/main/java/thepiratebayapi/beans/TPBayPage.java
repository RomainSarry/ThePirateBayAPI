package thepiratebayapi.beans;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Romain on 01/02/2018.
 */
public class TPBayPage {
    public TPBayPage(Document htmlDom) {
        this.htmlDom = htmlDom;
    }

    Document htmlDom;

    public Document getHtmlDom() {
        return htmlDom;
    }

    public Elements getFields(String selector) {
        return htmlDom.select(selector);
    }

    public Element getField(String selector) {
        return getFields(selector).first();
    }

    public Element getLastField(String selector) {
        return getFields(selector).last();
    }
}
