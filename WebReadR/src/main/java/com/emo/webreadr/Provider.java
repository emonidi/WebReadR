package com.emo.webreadr;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by egospodinov on 11/4/13.
 */
public class Provider {
    String name;
    ArrayList<Category> categories = new ArrayList<Category>();
    URL logoUrl;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }


    public void setLogoUrl (String url){
        try {
            URL u = new URL(url);
            this.logoUrl = u;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public URL getLogoUrl(){
        return this.logoUrl;
    }



}

