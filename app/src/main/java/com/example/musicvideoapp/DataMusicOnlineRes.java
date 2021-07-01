package com.example.musicvideoapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataMusicOnlineRes {
    @SerializedName("data_music_online")
    @Expose
    private List<DataMusicOnline> dataMusicOnline = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<DataMusicOnline> getDataMusicOnline() {
            return dataMusicOnline;
            }

    public void setDataMusicOnline(List<DataMusicOnline> dataMusicOnline) {
            this.dataMusicOnline = dataMusicOnline;
            }

    public Integer getSuccess() {
            return success;
            }

    public void setSuccess(Integer success) {
            this.success = success;
            }


public class DataMusicOnline {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("link")
        @Expose
        private String link;
        @SerializedName("author")
        @Expose
        private String author;
        @SerializedName("duration")
        @Expose
        private String duration;
        @SerializedName("icon")
        @Expose
        private String icon;

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

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

    }

}
