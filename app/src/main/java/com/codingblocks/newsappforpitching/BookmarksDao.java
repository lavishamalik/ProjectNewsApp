package com.codingblocks.newsappforpitching;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BookmarksDao {
    @Insert
    List<Long> insertNews(BookmarkNews... bookmarkNews);

    @Delete
    void deleteNews(BookmarkNews bookmarkNews);

    @Query("SELECT * FROM bookmarknews")
    LiveData<List<BookmarkNews>> getAllNews();

    @Query("SELECT * FROM bookmarknews WHERE id = :id")
    BookmarkNews getNewsById(Long id);

    @Update
    void updateNews(BookmarkNews bookmarkNews);
}
