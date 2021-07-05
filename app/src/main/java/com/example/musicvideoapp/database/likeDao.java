package com.example.musicvideoapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface likeDao {
    @Insert
    void addLike(Like like);

    @Query("SELECT * FROM tblLike where themeName= :themeName AND themeId= :themeId")
    Like getLike(String themeName,String themeId);

    @Query("SELECT * FROM tblLike")
    List<Like> getLikes();

    @Delete
    void removeLike(Like like);
}
