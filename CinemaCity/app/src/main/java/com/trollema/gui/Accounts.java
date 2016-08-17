package com.trollema.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;

import java.net.HttpURLConnection;
import java.util.InputMismatchException;
import android.os.AsyncTask;

import com.trollema.controllers.HTTPHandler;
import com.trollema.controllers.RequestHandler;
import com.trollema.controllers.StringValidator;

/**
 * Accounts Activity
 * Login and Register Screen Controller
 * @author Themiya D Chandraratna
 */
public class Accounts extends AppCompatActivity implements View.OnClickListener {

    EditText userNameField;
    EditText passWordField;
    TextView toggle;
    Boolean toggleOn = false;
    Button submitButton;
    String email;
    String password;
    String msg;

    @Override
    /**
     * Toggle Button Functionality
     * @author Themiya D Chandraratna
     */
    public void onClick(View view) {
        Log.d("Before", String.valueOf(toggleOn));
        userNameField.setText("");
        passWordField.setText("");
        if (toggleOn == false) {
            toggleOn = !toggleOn;
            submitButton.setBackgroundResource(R.drawable.register);
            toggle.setText("Already Registered? Login!");
        }
        else {
            toggleOn = !toggleOn;
            submitButton.setBackgroundResource(R.drawable.login);
            toggle.setText("New User? Register!");
        }
        Log.d("After", String.valueOf(toggleOn));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        userNameField = (EditText) findViewById(R.id.userName);
        passWordField = (EditText) findViewById(R.id.passWord);
        submitButton = (Button) findViewById(R.id.submit);
        toggle = (TextView) findViewById(R.id.toggle);
        toggle.setClickable(true);
        toggle.setOnClickListener(this);
    }

    /**
     * Sign Up or Login Button Functionality
     * @author Themiya D Chandraratna
     * @param view
     */
    public void signUpOrLogIn(View view) {
        msg = "Error!"; //default response
        email = String.valueOf(userNameField.getText()); //get email from view
        password = String.valueOf(passWordField.getText()); //get password from view
        boolean isEmpty = true;
        if ("".equals(email)||"".equals(password)) {
            isEmpty = true;
            msg = "One or more fields are empty!";
        }
        else {
            isEmpty = false;
            try {
                boolean validEmail = StringValidator.isEmail(email);
                if (!validEmail) {
                    isEmpty = true;
                    msg = "Please insert a valid email!";
                }
            }
            catch (Exception e) {
            }
        }
        //Log
        Log.i("AppInfo", String.valueOf(userNameField.getText()));
        Log.i("AppInfo", String.valueOf(passWordField.getText()));

        if (!isEmpty) {
            if (toggleOn) { //Sign up mode
                userNameField.setText("");
                passWordField.setText("");
                new MyRegistration().execute(email, password);
            } else { //Sign in mode
                userNameField.setText("");
                passWordField.setText("");
                new MyLogin().execute(email, password);
            }
        }
        else {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Login Thread
     * @author Themiya D Chandraratna
     */
    class MyLogin extends AsyncTask<String, Void, Void> {
        private boolean isValid;
        private boolean isBanned;
        private boolean isAdmin;

        private RequestHandler handler;

        public MyLogin(RequestHandler handler) {
            this.handler = handler;
        }

        public MyLogin() {
            handler = new HTTPHandler();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                isValid = MainActivity.myUserDataController.loginUser(params[0],params[1]);
                isBanned = MainActivity.myUser.bannedCode.equals("1");
                isAdmin = handler.isAdmin(params[0]);
                if (isBanned){
                    handler.logout(MainActivity.myUser.email);
                    msg = "Account Banned!";
                }
                if (isValid && !isBanned) {
                    if (!isAdmin) {
                        Intent i = new Intent(getApplicationContext(), MovieSearch.class);
                        startActivity(i);
                    }
                    else {
                        Intent i = new Intent(getApplicationContext(), AdminPanel.class);
                        startActivity(i);
                    }
                }
                else {
                    if (!isBanned) {
                        msg = "The Email and Password are Invalid!";
                    }
                }
            }
            catch (InputMismatchException e){
                msg = "The user is currently logged on from another device!";
            }
            catch (Exception e) {
                msg = "Database Connection Error!";
                Log.d("DatabaseConnection", e.getMessage());
            }
            return null;
        }
        protected void onPostExecute(Void result) {
            if(!isValid||isBanned) {
                Toast.makeText(Accounts.this, msg, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Registration Thread
     * @author Themiya D Chandraratna
     */
    class MyRegistration extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                boolean isCreated = MainActivity.myUserDataController.createUser(params[0],params[1]);
                if (isCreated) {
                    msg = "Account Created!";
                }
                else {
                    msg = "Sorry an Account with that Email already exists!";
                }
            }
            catch (Exception e) {
                msg = "Database Connection Error!";
//                Log.d("DatabaseConnection", "AccountCreationFailed");
            }
            return null;
        }
        protected void onPostExecute(Void result) {
            Toast.makeText(Accounts.this, msg, Toast.LENGTH_LONG).show();
        }
    }

    //go to movie search screen
    public void goHome(View view) {
        userNameField.setText("");
        passWordField.setText("");
        Intent i = new Intent(getApplicationContext(), MovieSearch.class);
        startActivity(i);
    }
}

