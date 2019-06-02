package com.example.sosimtapa;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Belal on 2/23/2017.
 */
@IgnoreExtraProperties
public class Upload{

    public String name;
    public String uri;
    public String content;





    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String name, String url,String content) {
        this.name = name;
        this.uri= url;
        this.content=content;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return uri;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String cotent) {
        this.content = cotent;
    }
}
