package com.trollema.gui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.trollema.appdata.models.User;
import com.trollema.controllers.HTTPHandler;
import com.trollema.controllers.MovieDataController;
import com.trollema.controllers.RequestHandler;
import com.trollema.gui.R;

import java.util.ArrayList;

public class AdminPanel extends AppCompatActivity {

    public static ArrayList<User> userList;
    ArrayAdapter adapter;
    ListView search_results;
    public static String userLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        search_results = (ListView) findViewById(R.id.listView);
        new AdminAction().execute();
    }

    /**
     * String formatter used to clean JSON Data
     * @author Themiya D Chandraratna
     */
    private String removeQuotes(String givenString){
        return givenString = givenString.replaceAll("\"", "");
    }

    /**
     * String formatter used to clean JSON Data
     * @author Themiya D Chandraratna
     */
    private String formatDescription(String givenString){
        return givenString = givenString.replaceAll("_", " ");
    }

    /**
     * Obtain User Profile
     * @author Themiya D Chandraratna
     * @param username : Desired User email
     * @return String[] in form [email,name,major,year,bio,img_id]
     */
    public String[] getProfile(String username, RequestHandler handler) {
        String[]myProfile = new String[6];
        String resultObj = handler.getProfile(username);
        resultObj = resultObj.replace("{", "");
        resultObj = resultObj.replace("}", "");
        String[]temp = resultObj.split(",");
        String myemail = temp[0].replace("\"email\":", "");
        myemail = removeQuotes(myemail);
        String myname = temp[1].replace("\"name\":", "");
        myname = removeQuotes(myname);
        String mymajor = temp[2].replace("\"major\":", "");
        mymajor = removeQuotes(mymajor);
        String myyear = temp[3].replace("\"year\":","");
        myyear = removeQuotes(myyear);
        String mybio = temp[4].replace("\"bio\":","");
        mybio = removeQuotes(mybio);
        String myimg = temp[5].replace("\"img_id\":","");
        myimg = removeQuotes(myimg);
        myProfile[0] = myemail;
        myProfile[1] = myname;
        myProfile[2] = mymajor;
        myProfile[3] = myyear;
        myProfile[4] = mybio;
        myProfile[5] = myimg;
        return myProfile;
    }

    /**
     * gets all the users in the db
     * @return ArrayList<User>
     */
    public ArrayList<User> getUsers(RequestHandler handler) {
        ArrayList<String> resultData = handler.getUsers();
        userList = new ArrayList<>();
        for (String record: resultData) {
            record = record.replace("{", "");
            record = record.replace("}", "");
            String[] myData = record.split(",");
            String email = myData[0].replace("\"email\":", "");
            email = removeQuotes(email); //user email
            String isActive = myData[2].replace("\"isActive\":", "");
            isActive = removeQuotes(isActive); //user activity
            String isBanned = myData[3].replace("\"isBanned\":", "");
            isBanned = removeQuotes(isBanned); //user isBanned
            String[] myProfile = getProfile(email, handler);
            User user = new User(email, myProfile[1], myProfile[2],
                    myProfile[3], myProfile[4], myProfile[5],
                    isActive, isBanned);
            userList.add(user);
        }
        return userList;
    }

    /**
     * AdminAction Thread
     * @author Themiya D Chandraratna
     */
    class AdminAction extends AsyncTask<String, Void, Void> {

        private RequestHandler handler;

        public AdminAction(RequestHandler handler) {
            this.handler = handler;
        }

        public AdminAction() {
            handler = new HTTPHandler();
        }

        @Override
        protected Void doInBackground(String ... inputs) {
            ArrayList<User> currentUsers = getUsers(handler);
            ArrayList<String> userNames = new ArrayList<>();
            for (User givenUser : currentUsers) {
                if (!handler.isAdmin(givenUser.email)) {
                    userNames.add(givenUser.email);
                }
            }
            adapter = new ArrayAdapter(AdminPanel.this, R.layout.activity_listviewadmin, userNames);
            return null;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(AdminPanel.this, "Please Wait...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(AdminPanel.this, "Search Results Shown", Toast.LENGTH_LONG).show();
            search_results.setAdapter(adapter);
        }
    }

    //go to ban activity
    public void goToBan(View view){
        TextView myUserLabel = (TextView) findViewById(R.id.username);
        userLabel = String.valueOf(myUserLabel.getText());
        Intent intent = new Intent(this, BanAccount.class);
        startActivity(intent);
    }

    /**
     * logout thread
     */
    class MyLogout extends AsyncTask<Void, Void, Void> {

        private RequestHandler handler;

        public MyLogout(RequestHandler handler) {
            this.handler = handler;
        }

        public MyLogout() {
            handler = new HTTPHandler();
        }


        @Override
        protected Void doInBackground(Void ... inputs) {
            handler.logout(MainActivity.myUser.email);
            MainActivity.myUser.email = "default";
            MainActivity.myUser.username = "default";
            MainActivity.myUser.major = "default";
            MainActivity.myUser.year = "0";
            MainActivity.myUser.bio = "default";
            MainActivity.myUser.img_id = "0";
            MainActivity.myUser.activeCode = "0";
            MainActivity.myUser.bannedCode = "-1";
            return null;
        }
        protected void onPostExecute(Void result) {
            Toast.makeText(AdminPanel.this, "Sucess!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * logout admin account
     * @param view
     */
    public void logoutAdmin(View view) {
        new MyLogout().execute();
        MainActivity.myUser.username = "default";
        MainActivity.myUser.major = "default";
        MainActivity.myUser.year = "0";
        MainActivity.myUser.bio = "default";
        MainActivity.myUser.img_id = "0";
        MainActivity.myUser.activeCode = "0";
        MainActivity.myUser.bannedCode = "-1";
        Intent intent = new Intent(this, MovieSearch.class);
        startActivity(intent);
    }
}
