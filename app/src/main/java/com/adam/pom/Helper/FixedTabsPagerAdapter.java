package com.adam.pom.Helper;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.adam.pom.Fragments.FragmentMain;
import com.adam.pom.Fragments.FragmentMatches;
import com.adam.pom.Fragments.FragmentSettings;

public class FixedTabsPagerAdapter extends FragmentStatePagerAdapter {

    public FixedTabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        //3 pages in the TabLayout
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentSettings();
            case 1:
                return new FragmentMain();
            case 2:
                return new FragmentMatches();
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Settings";
            case 1:
                return "Home";
            case 2:
                return "Matches";
        }
        return null;
    }
}
