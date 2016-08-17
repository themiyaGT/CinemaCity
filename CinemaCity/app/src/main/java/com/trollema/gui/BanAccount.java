package com.trollema.gui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.trollema.appdata.models.User;
import com.trollema.controllers.HTTPHandler;
import com.trollema.controllers.RequestHandler;
import com.trollema.gui.R;

import java.util.ArrayList;

public class BanAccount extends AppCompatActivity {

    TextView username;
    User user;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        username = (TextView) findViewById(R.id.accountName);
        findUser();
        if (user.bannedCode.equals("1")) {
            status = "banned";
        }
        else {
            status = "not banned";
        }
        username.setText("The user "+user.email+" is "+status);
    }

    //finds current user
    private void findUser(){
        ArrayList<User> myUsers = AdminPanel.userList;
        for (User givenUser: myUsers) {
            if (AdminPanel.userLabel.equals(givenUser.email)) {
                user = givenUser;
            }
        }
    }

    public void banAccount(View view) {
        new BanThisAccount().execute();
        username.setText("The user "+user.email+" is banned");
    }

    public void undoBan(View view) {
        new UnlockThisAccount().execute();
        username.setText("The user "+user.email+" is not banned");
    }

    class BanThisAccount extends AsyncTask<Void, Void, Void> {
        private RequestHandler handler;

        public BanThisAccount() {
            handler = new HTTPHandler();
        }

        public BanThisAccount(RequestHandler handler) {
            this.handler = handler;
        }

        @Override
        protected Void doInBackground(Void ... inputs) {
            handler.banUser(user.email);
            return null;
        }
        protected void onPostExecute(Void result) {
            Toast.makeText(BanAccount.this, "Account Banned!", Toast.LENGTH_LONG).show();
        }
    }

    class UnlockThisAccount extends AsyncTask<Void, Void, Void> {
        private RequestHandler handler;

        public UnlockThisAccount() {
            handler = new HTTPHandler();
        }

        public UnlockThisAccount(RequestHandler handler) {
            this.handler = handler;
        }

        @Override
        protected Void doInBackground(Void ... inputs) {
            handler.undoBan(user.email);
            return null;
        }
        protected void onPostExecute(Void result) {
            Toast.makeText(BanAccount.this, "Account Unlocked!", Toast.LENGTH_LONG).show();
        }
    }

}
