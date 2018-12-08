package com.codingblocks.newsappforpitching;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
@Database(entities = {BookmarkNews.class},version = 1)
public abstract class BookmarksDatabase extends RoomDatabase{

    public abstract BookmarksDao getNewsDao();
}
