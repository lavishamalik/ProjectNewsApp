package com.codingblocks.newsappforpitching.fragment;

import android.annotation.SuppressLint;

import com.codingblocks.newsappforpitching.BookmarksDatabase;

@SuppressLint("ValidFragment")
public class HomeFragment extends BaseArticlesFragment {

    public static final String LOG_TAG = HomeFragment.class.getName();

    public HomeFragment(BookmarksDatabase bookmarksDatabase) {
        super(bookmarksDatabase);
    }
}
