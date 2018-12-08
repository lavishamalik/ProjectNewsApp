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
public class SportFragment extends BaseArticlesFragment {

    private static final String LOG_TAG = SportFragment.class.getName();

    public SportFragment(BookmarksDatabase bookmarksDatabase) {
        super(bookmarksDatabase);
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        String sportUrl = NewsPreferences.getPreferredUrl(getContext(), getString(R.string.sport));
        Log.e(LOG_TAG, sportUrl);

        return new NewsLoader(getActivity(), sportUrl);
    }
}
