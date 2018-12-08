package com.codingblocks.newsappforpitching;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

public class NoteApplication extends Application {

    static BookmarksDatabase bookmarksDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    static BookmarksDatabase getBookmarksDatabase(Context context) {
        if (bookmarksDatabase == null) {
            bookmarksDatabase = Room.databaseBuilder(context,
                    BookmarksDatabase.class,
                    "noteDatabase.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return bookmarksDatabase;
    }

}
