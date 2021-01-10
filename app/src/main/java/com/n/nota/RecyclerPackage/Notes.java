package com.n.nota.RecyclerPackage;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Notes {
    private String key ;
    private String title ;
    private String description ;
    private String image ;

    public Notes() {
    }

    public Notes(String key, String title, String description) {
        this.key = key;
        this.title = title;
        this.description = description;
    }

    public Notes(String key, String title, String description, String image) {
        this.key = key;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
