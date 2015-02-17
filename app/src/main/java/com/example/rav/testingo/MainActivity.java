package com.example.rav.testingo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rav.testingo.DataFlow.DataClient;
import com.example.rav.testingo.DataFlow.ErrorResponseEvent;
import com.example.rav.testingo.DataFlow.HttpDataClient;
import com.example.rav.testingo.DataFlow.JsonResponseEvent;
import com.example.rav.testingo.DataStructures.TestDetailInfo;
import com.example.rav.testingo.DataStructures.UserSelfAccount;
import com.yelp.android.webimageview.ImageLoader;

import de.greenrobot.event.EventBus;


public class MainActivity extends ActionBarActivity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks,
        MainActivityInteractions
{
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private UserSelfAccount profile;
    private boolean backToTest = false;
    private String lastTestId = "";
    private DataClient client;
//    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageLoader.initialize(this, null);
        client = new HttpDataClient(getResources().getString(R.string.base_url), this);

        setContentView(R.layout.activity_nav_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
        actionBar.setLogo(R.drawable.ic_launcher);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

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

    public void switchFragment(Fragment fragment, boolean animate, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(animate)
            ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.replace(R.id.fragment_target, fragment);
        if(addToBackStack) ft.addToBackStack(null);
        ft.commit();

//        if(mNavigationDrawerFragment != null)
//            mNavigationDrawerFragment.lockNav(false);
        backToTest = false;
        client.get("mobile/notifications/count", NavigationDrawerFragment.NOTIFICATIONS_COUNT_RESPONSE);
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
    public UserSelfAccount getSelfAccount() {
        return profile;
    }

    @Override
    public void showFeed() {
        switchFragment(FeedActivityFragment.newInstance(false), true, false);
    }

    @Override
    public void showChannel(String id) {
        switchFragment(ChannelFragment.newInstance(id), true, true);
    }

    @Override
    public void showTestDetails(String id) {
        lastTestId = id;
        switchFragment(TestDetailsFragment.newInstance(id), true, true);
    }

    @Override
    public void startTest(String token, String testName, int qCount) {
        switchFragment(TestFragment.newInstance(token, testName, qCount), true, true);
//        mNavigationDrawerFragment.lockNav(true);
    }

    @Override
    public void showSubscriptions() {
        switchFragment(SubscriptionsFragment.newInstance(), true, false);
    }

    @Override
    public void showResultList() {
        switchFragment(ResultListFragment.newInstance(), true, false);
    }

    @Override
    public void setTitle(String title) {
        mTitle = title;
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void showResultDetails(String id, boolean backToTest) {
        switchFragment(ResultFragment.newInstance(id), true, true);
        this.backToTest = backToTest;
    }

    @Override
    public void showSettings (){

    }

    @Override
    public void showNotifications(){
        switchFragment(FeedActivityFragment.newInstance(true), true, true);
    }

    @Override
    public void onBackPressed() {
        if(!backToTest)
            super.onBackPressed();
        else showTestDetails(lastTestId);
    }
}
