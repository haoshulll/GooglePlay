package com.myapp.googleplay.ui.activity;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.myapp.googleplay.R;
import com.myapp.googleplay.ui.adapter.MainPagerAdapter;
import com.myapp.googleplay.lib.pagerslidingtab.PagerSlidingTab;

import butterknife.InjectView;

public class MainActivity extends BaseActivity {
    private ActionBarDrawerToggle drawerToggle;

    @InjectView(R.id.viewPager) ViewPager viewPager;
    @InjectView(R.id.slidingTab) PagerSlidingTab slidingTab;
    @InjectView(R.id.drawer_layout) DrawerLayout drawer_layout;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initActionBar() {
        //1.获取V7包的actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.ic_launcher);
        actionBar.setTitle(R.string.app_name);

        //2.设置actionbar的home按钮可用
        actionBar.setDisplayHomeAsUpEnabled(true);//设置可以被点击
        actionBar.setDisplayShowHomeEnabled(true);//设置可见

        //3.设置Toggle开关，用来点击可以开启和关闭menu
        drawerToggle = new ActionBarDrawerToggle(this, drawer_layout, 0, 0);
        drawerToggle.syncState();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        slidingTab.setViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        drawerToggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }


}
