package com.example.musicvideoapp;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TableMagicSlideshowRes {

    @SerializedName("table_magic_slideshow")
    @Expose
    private List<TableMagicSlideshow> tableMagicSlideshow = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<TableMagicSlideshow> getTableMagicSlideshow() {
        return tableMagicSlideshow;
    }

    public void setTableMagicSlideshow(List<TableMagicSlideshow> tableMagicSlideshow) {
        this.tableMagicSlideshow = tableMagicSlideshow;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public class TableMagicSlideshow {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("link")
        @Expose
        private String link;
        @SerializedName("lock")
        @Expose
        private String lock;
        @SerializedName("youtube")
        @Expose
        private String youtube;
        @SerializedName("text_name")
        @Expose
        private String textName;
        @SerializedName("download_count")
        @Expose
        private String downloadCount;
        @SerializedName("size_mb")
        @Expose
        private String sizeMb;
        @SerializedName("like_photo")
        @Expose
        private String likePhoto;
        @SerializedName("music")
        @Expose
        private String music;

        private boolean isLike=false;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getLock() {
            return lock;
        }

        public void setLock(String lock) {
            this.lock = lock;
        }

        public String getYoutube() {
            return youtube;
        }

        public void setYoutube(String youtube) {
            this.youtube = youtube;
        }

        public String getTextName() {
            return textName;
        }

        public void setTextName(String textName) {
            this.textName = textName;
        }

        public String getDownloadCount() {
            return downloadCount;
        }

        public void setDownloadCount(String downloadCount) {
            this.downloadCount = downloadCount;
        }

        public String getSizeMb() {
            return sizeMb;
        }

        public void setSizeMb(String sizeMb) {
            this.sizeMb = sizeMb;
        }

        public String getLikePhoto() {
            return likePhoto;
        }

        public void setLikePhoto(String likePhoto) {
            this.likePhoto = likePhoto;
        }

        public String getMusic() {
            return music;
        }

        public void setMusic(String music) {
            this.music = music;
        }

        public boolean isLike() {
            return isLike;
        }

        public void setLike(boolean like) {
            isLike = like;
        }
    }
}
