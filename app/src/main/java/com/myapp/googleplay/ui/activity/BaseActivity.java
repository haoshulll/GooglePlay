package com.myapp.googleplay.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends ActionBarActivity{
	protected ActionBar mActionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		ButterKnife.inject(this);
		initActionBar();
		initListener();
		initData();
	}
	
	protected abstract void initView();
	protected abstract void initActionBar();
	protected abstract void initListener();
	protected abstract void initData();
}
