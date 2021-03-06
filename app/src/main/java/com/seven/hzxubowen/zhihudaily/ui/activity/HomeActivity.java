package com.seven.hzxubowen.zhihudaily.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.seven.hzxubowen.zhihudaily.R;
import com.seven.hzxubowen.zhihudaily.Util.OnFragmentInteractionListener;
import com.seven.hzxubowen.zhihudaily.ui.fragment.ContentFragment;
import com.seven.hzxubowen.zhihudaily.ui.fragment.HomeFragment;
import com.seven.hzxubowen.zhihudaily.ui.fragment.TitleFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {

    private int currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //设置初始Fragment
        navigationView.setCheckedItem(R.id.nav_home);
        currentId = R.id.nav_home;
        Fragment fragment = HomeFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fr_container, fragment).commit();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if(currentId != id){
            switch(id){
                case R.id.nav_home:
                    fragment = HomeFragment.newInstance();
                    getSupportActionBar().setTitle("首页");
                    break;
                case R.id.nav_company:
                    fragment = TitleFragment.newInstance("大公司日报" , "5");
                    getSupportActionBar().setTitle("大公司日报");
                    break;
                case R.id.nav_design:
                    fragment = TitleFragment.newInstance("设计日报", "4");
                    getSupportActionBar().setTitle("设计日报");
                    break;
                case R.id.nav_finance:
                    fragment = TitleFragment.newInstance("财经日报", "6");
                    getSupportActionBar().setTitle("财经日报");
                    break;
                case R.id.nav_movie:
                    fragment = TitleFragment.newInstance("电影日报", "3");
                    getSupportActionBar().setTitle("电影日报");
                    break;
                case R.id.nav_phi:
                    fragment = TitleFragment.newInstance("日常心理学", "13");
                    getSupportActionBar().setTitle("日常心理学");
                    break;
                case R.id.nav_rec:
                    fragment = TitleFragment.newInstance("用户推荐日报", "12");
                    getSupportActionBar().setTitle("用户推荐日报");
                    break;
                case R.id.nav_security:
                    fragment = TitleFragment.newInstance("互联网安全", "10");
                    getSupportActionBar().setTitle("互联网安全");
                    break;
                case R.id.nav_boring:
                    fragment = TitleFragment.newInstance("不许无聊", "11");
                    getSupportActionBar().setTitle("不许无聊");
                    break;
                case R.id.nav_sports:
                    fragment = TitleFragment.newInstance("体育日报", "8");
                    getSupportActionBar().setTitle("体育日报");
                    break;
            }
            currentId = id;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if(fragment != null){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.replace(R.id.fr_container, fragment).commit();
        }
        return true;
    }

    public void onFragmentInteraction(String newsId, ArrayList<String> idList){
        Intent intent = new Intent(HomeActivity.this, NewsActivity.class);
        intent.putStringArrayListExtra(NewsActivity.NEWS_LIST, idList);
        intent.putExtra(NewsActivity.NEWS_ID, newsId);
        startActivity(intent);
        
    }
}
