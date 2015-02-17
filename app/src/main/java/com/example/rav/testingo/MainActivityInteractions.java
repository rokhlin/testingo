package com.example.rav.testingo;

import com.example.rav.testingo.DataStructures.TestDetailInfo;
import com.example.rav.testingo.DataStructures.UserSelfAccount;

/**
 * Created by Shtutman on 04.02.2015.
 */
public interface MainActivityInteractions {
    void showFeed();
    void showChannel(String id);
    void showTestDetails(String id);
    void startTest(String token, String testName, int qCount);
    void showSubscriptions();
    void showResultList();
    void showResultDetails(String id, boolean backToTest);
    void showSettings();
    void showProfile();
    void showNotifications();
    void setTitle(String title);
    UserSelfAccount getSelfAccount();
}
