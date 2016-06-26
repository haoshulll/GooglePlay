package com.myapp.googleplay.ui.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by haoshul on 2016/6/21.
 */
public class FragmentFactory {
    	public static Fragment createFragment(int position){
		Fragment fragment = null;
		if(fragment==null){
			switch (position) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new AppFragment();
				break;
			case 2:
				fragment = new GameFragment();
				break;
			case 3:
				fragment = new SubjectFragment();
				break;
			case 4:
				fragment = new RecommendFragment();
				break;
			case 5:
				fragment = new CategoryFragment();
				break;
			case 6:
				fragment = new HotFragment();
				break;
			}
		}
		return fragment;
	}
}
