package com.example.rav.testingo;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rav.testingo.DataStructures.TestDetailInfo;
import com.yelp.android.webimageview.ImageLoader;


public class MainActivity extends ActionBarActivity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks,
        MainActivityInteractions
{
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageLoader.initialize(this, null);
        setContentView(R.layout.activity_nav_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
             .add(R.id.fragment_target, FeedActivityFragment.newInstance())
             .commit();
        }
    }




    @Override
    public void onNavigationDrawerItemSelected(int position) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .addToBackStack(null)
//                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
//                        android.R.anim.slide_in_left, android.R.anim.slide_out_right)
////              .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
//                .commit();
        switch (position) {
            case 0: showFeed(); break;
            case 1: showResultList(); break;
            case 2: showSubscriptions(); break;
            case 3: showNotifications(); break;
            case 4: showSettings(); break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.nav_feed, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

//    public void onSectionAttached(int number) {
//        switch (number) {
//            case 1:
//                mTitle = getString(R.string.title_section1);
//                break;
//            case 2:
//                mTitle = getString(R.string.title_section2);
//                break;
//            case 3:
//                mTitle = getString(R.string.title_section3);
//                break;
//        }
//    }

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
    public void showFeed() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                        android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fragment_target, FeedActivityFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showChannel(String id) {

    }

    @Override
    public void showTestDetails(String id) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                        android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fragment_target, TestDetailsFragment.newInstance(id))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void startTest(TestDetailInfo test) {

    }

    @Override
    public void showSubscriptions() {
        getSupportFragmentManager().beginTransaction()
        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        .replace(R.id.fragment_target, SubscriptionsFragment.newInstance())
        .addToBackStack(null)
        .commit();
    }

    @Override
    public void showResultList() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                        android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fragment_target, ResultListFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showResultDetails(String id) {

    }

    @Override
    public void showSettings (){

    }

    @Override
    public void showNotifications(){

    }
}