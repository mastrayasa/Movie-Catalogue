package com.dicoding.picodiploma.academy.ui.main;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dicoding.picodiploma.academy.FavoriteTvFragment;
import com.dicoding.picodiploma.academy.R;
import com.dicoding.picodiploma.academy.FavoriteMovieFragment;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_film, R.string.tab_tv};
    private final Context mContext;

    private Fragment[] childFragments;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

        childFragments = new Fragment[] {
                new FavoriteMovieFragment(),
                new FavoriteTvFragment()
        };

    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return childFragments[position];
       /* switch (position) {
            case 0:
                return FavoriteMovieFragment.newInstance();
            case 1:
                return PlaceholderFragment.newInstance(position + 1);

        }

        return null;*/


    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}