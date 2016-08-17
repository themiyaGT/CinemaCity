package com.trollema.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.widget.ImageView;
import android.content.Intent;
import com.trollema.appdata.models.User;
import com.trollema.controllers.HTTPHandler;
import com.trollema.controllers.UserDataController;

/**
 * Main Activity
 * Loads logos and transitions to Movie Search Screen
 * @author Themiya D Chandraratna
 */
public class MainActivity extends AppCompatActivity {

    public static User myUser;
    public static UserDataController myUserDataController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create default user
        myUser = new User();
        //Data Controller applied to default user
        myUserDataController = new UserDataController(myUser, new HTTPHandler());
        Handler h = new Handler();
        h.postDelayed(animation, 2000);
        h.postDelayed(transition, 3500);
    }

    /**
     * Animation Script
     */
    Runnable animation = new Runnable() {
        @Override
        public void run(){
            ImageView logo = (ImageView) findViewById(R.id.logo);
            logo.animate().alpha(0f).setDuration(2000);
            ImageView teamLogo = (ImageView) findViewById(R.id.teamLogo);
            teamLogo.animate().alpha(0.6f).setDuration(2000);
        }
    };

    /**
     * Transition Script
     */
    Runnable transition = new Runnable() {
        @Override
        public void run(){
            Intent i = new Intent(getApplicationContext(), MovieSearch.class);
            startActivity(i);
        }
    };
}
