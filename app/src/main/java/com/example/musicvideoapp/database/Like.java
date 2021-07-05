package com.example.musicvideoapp.database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tblLike")
public class Like implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "likeId")
    private int likeId;
    @NonNull
    @ColumnInfo(name = "themeName")
    private String themeName;
    @ColumnInfo(name = "themeId")
    private String themeId;

    public Like(@NonNull String themeName, String themeId) {
        this.themeName = themeName;
        this.themeId = themeId;
    }

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    @NonNull
    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(@NonNull String themeName) {
        this.themeName = themeName;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }
}

