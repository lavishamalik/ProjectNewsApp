package com.codingblocks.newsappforpitching.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.Loader;
import android.util.Log;

import com.codingblocks.newsappforpitching.BookmarksDatabase;
import com.codingblocks.newsappforpitching.News;
import com.codingblocks.newsappforpitching.NewsLoader;
import com.codingblocks.newsappforpitching.NewsPreferences;
import com.codingblocks.newsappforpitching.R;

import java.util.List;


@SuppressLint("ValidFragment")
public class CultureFragment extends BaseArticlesFragment {

    private static final String LOG_TAG = CultureFragment.class.getName();

    @SuppressLint("ValidFragment")
    public CultureFragment(BookmarksDatabase bookmarksDatabase) {
        super(bookmarksDatabase);
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        String cultureUrl = NewsPreferences.getPreferredUrl(getContext(), getString(R.string.culture));
        Log.e(LOG_TAG, cultureUrl);

        return new NewsLoader(getActivity(), cultureUrl);
    }
}
