package com;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

import blitzboba.blitzboba.AboutUs;
import blitzboba.blitzboba.ContactUs;
import blitzboba.blitzboba.Events;
import blitzboba.blitzboba.Menu;

/**
 * Created by Rodrigo on 10/18/2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    Context mContext;


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        Fragment fragment;

        switch(position) {
            case 0:
                fragment = new AboutUs();
                return fragment;
            case 1:
                fragment = new Menu();
                return fragment;
            case 2:
                fragment = new Events();
                return fragment;
            case 3:
                fragment = new ContactUs();
                return fragment;
        }
        return null;
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
