package com.example.sosimtapa;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Belal on 2/23/2017.
 */
@IgnoreExtraProperties
public class Upload{

    public String name;
    public String uri;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String name, String url) {
        this.name = name;
        this.uri= url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return uri;
    }
}
