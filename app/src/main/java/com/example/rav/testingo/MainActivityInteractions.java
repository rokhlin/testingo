package com.example.rav.testingo;

import com.example.rav.testingo.DataStructures.TestDetailInfo;

/**
 * Created by Shtutman on 04.02.2015.
 */
public interface MainActivityInteractions {
    void showFeed();
    void showChannel(String id);
    void showTestDetails(String id);
    void startTest(TestDetailInfo test);
    void showSubscriptions();
    void showResultList();
    void showResultDetails(String id);
    void showSettings();
    void showNotifications();
}
