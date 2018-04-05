package com.hapramp.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ankit on 10/22/2017.
 */

public class SkillsModel {

    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("image_uri")
    public String image_uri;
    @SerializedName("description")
    public String description;

    public SkillsModel(int id, String name, String image_uri, String description) {
        this.id = id;
        this.name = name;
        this.image_uri = image_uri;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SkillsModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image_uri='" + image_uri + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


}
