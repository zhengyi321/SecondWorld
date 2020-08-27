package com.et.secondworld.fragment.firstpage;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class FirstFragmentPageAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mList;
    private ArrayList<String> mListString;
    public FirstFragmentPageAdapter(FragmentManager fm, ArrayList<Fragment> mList, ArrayList<String> mListString) {
        super(fm);
        this.mList = mList;
        this.mListString = mListString;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mListString.get(position);
    }
}
