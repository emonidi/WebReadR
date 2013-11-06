package com.emo.webreadr;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by egospodinov on 11/4/13.
 */
public class Category {
       String name = null;
       URL url = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
