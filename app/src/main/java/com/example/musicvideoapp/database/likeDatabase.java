package com.example.musicvideoapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.musicvideoapp.items.Constant;

@Database(entities = {Like.class},version = 1,exportSchema = false)
public abstract class likeDatabase extends RoomDatabase {
    public abstract likeDao likeDao();
    private static volatile likeDatabase INSTANCE;

    public static likeDatabase getInstance(Context context){
        if (INSTANCE==null){
            synchronized (likeDatabase.class){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            likeDatabase.class, Constant.tblLike)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
