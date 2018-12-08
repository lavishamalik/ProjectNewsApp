package com.codingblocks.newsappforpitching.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingblocks.newsappforpitching.BookmarksDatabase;
import com.codingblocks.newsappforpitching.News;
import com.codingblocks.newsappforpitching.NewsLoader;
import com.codingblocks.newsappforpitching.NewsPreferences;
import com.codingblocks.newsappforpitching.NoteApplication;
import com.codingblocks.newsappforpitching.R;
import com.codingblocks.newsappforpitching.adapter.NewsListViewAdapter;
import com.codingblocks.newsappforpitching.Constants;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.flipview.FlipView;
@SuppressLint("ValidFragment")
public class BaseArticlesFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<News>>{

    BookmarksDatabase bookmarksDatabase;
    private static final String LOG_TAG = BaseArticlesFragment.class.getName();

    private static final int NEWS_LOADER_ID = 1;


    private NewsListViewAdapter mAdapter;
   private View mLoadingIndicator;

    @SuppressLint("ValidFragment")
    public BaseArticlesFragment(BookmarksDatabase bookmarksDatabase) {
        this.bookmarksDatabase = bookmarksDatabase;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        mLoadingIndicator = rootView.findViewById(R.id.loading_indicator);


       FlipView flipView=rootView.findViewById(R.id.flip_view);
        mAdapter = new NewsListViewAdapter( new ArrayList<News>(),getContext()/*bookmarksDatabase*/);

      flipView.setAdapter(mAdapter);

        initializeLoader(isConnected());

        return rootView;
    }


    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        Uri.Builder uriBuilder = NewsPreferences.getPreferredUri(getContext());

        Log.e(LOG_TAG,uriBuilder.toString());
        return new NewsLoader(getActivity(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> newsData) {
        mLoadingIndicator.setVisibility(View.GONE);
        mAdapter.clearAll();
        if (newsData != null && !newsData.isEmpty()) {
            mAdapter.addAll(newsData);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        mAdapter.clearAll();
    }

    @Override
    public void onResume() {
        super.onResume();
        restartLoader(isConnected());
    }
    private boolean isConnected() {
             ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    private void initializeLoader(boolean isConnected) {
        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {

            mLoadingIndicator.setVisibility(View.GONE);
            }
    }
    private void restartLoader(boolean isConnected) {
        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
           loaderManager.restartLoader(NEWS_LOADER_ID, null, this);
        } else {
            mLoadingIndicator.setVisibility(View.GONE);
              }
    }

    private void initiateRefresh() {
        restartLoader(isConnected());
    }
}
