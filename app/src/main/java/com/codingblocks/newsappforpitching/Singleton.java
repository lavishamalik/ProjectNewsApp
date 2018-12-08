package com.codingblocks.newsappforpitching;

public final class Singleton {

    public BookmarksDatabase bookmarksDatabase;

    public Singleton() {
    }

    public BookmarksDatabase getBookmarksDatabase() {
        return bookmarksDatabase;
    }

    public void setBookmarksDatabase(BookmarksDatabase bookmarksDatabase) {
        this.bookmarksDatabase = bookmarksDatabase;
    }
}
