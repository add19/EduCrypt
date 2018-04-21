package com.example.aadishdeshpande.educrypt;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;

/**
 * Created by Aadish Deshpande on 4/5/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;
    public  PagerAdapter(FragmentManager fm,int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                News news = new News();
                return news;
            case 1:
                Wallet wallet = new Wallet();
                return wallet;
            case 2:
                Guide guide = new Guide();
                return guide;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
