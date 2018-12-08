package com.codingblocks.newsappforpitching.adapter;

import android.arch.persistence.room.Database;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;

import com.codingblocks.newsappforpitching.BookmarksDatabase;
import com.codingblocks.newsappforpitching.R;
import com.codingblocks.newsappforpitching.fragment.BusinessFragment;
import com.codingblocks.newsappforpitching.fragment.CultureFragment;
import com.codingblocks.newsappforpitching.fragment.EnvironmentFragment;
import com.codingblocks.newsappforpitching.fragment.FashionFragment;
import com.codingblocks.newsappforpitching.fragment.HomeFragment;
import com.codingblocks.newsappforpitching.fragment.ScienceFragment;
import com.codingblocks.newsappforpitching.fragment.SocietyFragment;
import com.codingblocks.newsappforpitching.fragment.SportFragment;
import com.codingblocks.newsappforpitching.fragment.WorldFragment;
import com.codingblocks.newsappforpitching.Constants;




public class CategoryFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    BookmarksDatabase database;
    public CategoryFragmentPagerAdapter(Context context, FragmentManager fm, BookmarksDatabase database) {
        super(fm);
        mContext = context;
        this.database=database;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case Constants.HOME:
                return new HomeFragment(database);
            case Constants.WORLD:
                return new WorldFragment(database);
            case Constants.SCIENCE:
                return new ScienceFragment(database);
            case Constants.SPORT:
                return new SportFragment(database);
            case Constants.ENVIRONMENT:
                return new EnvironmentFragment(database);
            case Constants.SOCIETY:
                return new SocietyFragment(database);
            case Constants.FASHION:
                return new FashionFragment(database);
            case Constants.BUSINESS:
                return new BusinessFragment(database);
            case Constants.CULTURE:
                return new CultureFragment(database);
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int titleResId;
        switch (position) {
            case Constants.HOME:
                titleResId = R.string.ic_title_home;
                break;
            case Constants.WORLD:
                titleResId = R.string.ic_title_world;
                break;
            case Constants.SCIENCE:
                titleResId = R.string.ic_title_science;
                break;
            case Constants.SPORT:
                titleResId = R.string.ic_title_sport;
                break;
            case Constants.ENVIRONMENT:
                titleResId = R.string.ic_title_environment;
                break;
            case Constants.SOCIETY:
                titleResId = R.string.ic_title_society;
                break;
            case Constants.FASHION:
                titleResId = R.string.ic_title_fashion;
                break;
            case Constants.BUSINESS:
                titleResId = R.string.ic_title_business;
                break;
                default:
                titleResId =R.string.ic_title_culture;
                break;
        }
        return mContext.getString(titleResId);
    }
}