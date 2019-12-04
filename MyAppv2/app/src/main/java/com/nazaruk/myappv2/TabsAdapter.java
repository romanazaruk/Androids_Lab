package com.nazaruk.myappv2;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.nazaruk.myappv2.fragments.EmptyFragment;
import com.nazaruk.myappv2.fragments.MoviesFragment;
import com.nazaruk.myappv2.fragments.MyProfileFragment;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TabsAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                MoviesFragment moviesFragment = new MoviesFragment();
                return moviesFragment;
            case 1:
                EmptyFragment emptyFragment = new EmptyFragment();
                return emptyFragment;
            case 2:
                MyProfileFragment myProfileFragment = new MyProfileFragment();
                return myProfileFragment;
            default:
                MoviesFragment defaultFragment = new MoviesFragment();
                return defaultFragment;
        }
    }
}