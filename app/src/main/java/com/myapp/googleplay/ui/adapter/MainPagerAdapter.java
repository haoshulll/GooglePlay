package com.myapp.googleplay.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.myapp.googleplay.R;
import com.myapp.googleplay.global.GooglePlayApplication;
import com.myapp.googleplay.ui.fragment.FragmentFactory;

public class MainPagerAdapter extends FragmentPagerAdapter {
	private String[] tabs;
	public MainPagerAdapter(FragmentManager fm) {
		super(fm);
		tabs = GooglePlayApplication.getContext().getResources().getStringArray(R.array.tab_names);
	}

	@Override
	public Fragment getItem(int position) {
		return FragmentFactory.createFragment(position);
	}

	@Override
	public int getCount() {
		return tabs.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return tabs[position];
	}
}