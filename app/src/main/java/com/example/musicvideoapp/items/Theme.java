package com.example.musicvideoapp.items;

public class Theme {
    private String name;
    private int imageId;

    public Theme(String name, int imageId){
        this.name = name;
        this.imageId = imageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }


    public int getImageId() {
        return imageId;
    }

}