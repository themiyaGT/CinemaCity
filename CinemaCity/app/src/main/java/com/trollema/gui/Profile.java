package com.trollema.gui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import com.trollema.controllers.HTTPHandler;
import com.trollema.appdata.constants.MajorData;
import com.trollema.controllers.RequestHandler;

/**
 * Profile Activity
 * Profile Screen
 * @author Themiya D Chandraratna
 */
public class Profile extends AppCompatActivity {
    String msg = "";
    //Editable States --tracks whether a given field is editable or not
    int editNameState = 0;
    int editMajorState = 0;
    int editYearState = 0;
    int editBioState = 0;

    //Edit Icons --handles for the edit icons
    Button nameIc;
    Button majorIc;
    Button yearIc;
    Button bioIc;

    //TextFields
    TextView email;
    EditText name;
    AutoCompleteTextView major;
    EditText year;
    EditText bio;

    //User Account Status
    ImageView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Initial States
        editNameState = 0;
        editMajorState = 0;
        editYearState = 0;
        editBioState = 0;
        //Grasp View
        //<--------->
        nameIc = (Button) findViewById(R.id.edit_name);
        majorIc = (Button) findViewById(R.id.edit_major);
        yearIc = (Button) findViewById(R.id.edit_year);
        bioIc = (Button) findViewById(R.id.edit_bio);
        //<--------->
        email = (TextView) findViewById(R.id.email);
        name = (EditText) findViewById(R.id.name);
        name.clearFocus();
        major = (AutoCompleteTextView) findViewById(R.id.major);
        major.clearFocus();
        year = (EditText) findViewById(R.id.year);
        year.clearFocus();
        bio = (EditText) findViewById(R.id.bio);
        bio.clearFocus();
        status = (ImageView) findViewById(R.id.status_circle);
        //<--------->
        //Hide Buttons
        nameIc.setVisibility(View.INVISIBLE);
        majorIc.setVisibility(View.INVISIBLE);
        yearIc.setVisibility(View.INVISIBLE);
        bioIc.setVisibility(View.INVISIBLE);
        //Disable edit Text
        name.setEnabled(false);
        major.setEnabled(false);
        year.setEnabled(false);
        bio.setEnabled(false);
        //Load Profile
        email.setText(MainActivity.myUser.email);
        if (!MainActivity.myUser.username.equals("default")){
            name.setText(MainActivity.myUser.username);
        }
        if (!MainActivity.myUser.major.equals("default")){
            major.setText(MainActivity.myUser.major);
        }
        if (!MainActivity.myUser.year.equals("0")){
            year.setText(MainActivity.myUser.year);
        }
        if (!MainActivity.myUser.bio.equals("null")){
            bio.setText(MainActivity.myUser.bio);
        }
        //Set Status label
        if (MainActivity.myUser.bannedCode.equals("0")){
            status.setBackgroundResource(R.drawable.green_circle);
        }
        else{
            status.setBackgroundResource(R.drawable.red_circle);
        }

        //search results enabled major textbox
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, MajorData.validMajor);
        major.setAdapter(adapter);
    }

    public void nameEdit(View v) {
        editName(v);
    }

    public void majorEdit(View v) {
        editMajor(v);
    }

    public void yearEdit(View v) {
        editYear(v);
    }

    public void bioEdit(View v) {
        editBio(v);
    }

    /**
     * Name Field Functionality
     * @author Themiya D Chandraratna
     * @param view
     */
    public void editName(View view) {
        if (editNameState == 1){
            editNameState--;
            nameIc.setVisibility(View.INVISIBLE);
            name.setEnabled(false);
            name.clearFocus();
        }
        else{ //editable
            editNameState++;
            nameIc.setVisibility(View.VISIBLE);
            name.setEnabled(true);
        }
    }

    /**
     * Major Field Functionality
     * @author Themiya D Chandraratna
     * @param view;
     */
    public void editMajor(View view){
        if (editMajorState == 1){
            editMajorState--;
            majorIc.setVisibility(View.INVISIBLE);
            major.setEnabled(false);
            major.clearFocus();
        }
        else{
            editMajorState++;
            majorIc.setVisibility(View.VISIBLE);
            major.setEnabled(true);
        }
    }

    /**
     * Year Field Functionality
     * @author Themiya D Chandraratna
     * @param view
     */
    public void editYear(View view){
        if (editYearState == 1){
            editYearState--;
            yearIc.setVisibility(View.INVISIBLE);
            year.setEnabled(false);
            year.clearFocus();
        }
        else{
            editYearState++;
            yearIc.setVisibility(View.VISIBLE);
            year.setEnabled(true);
        }
    }

    /**
     * Bio Field Functionality
     * @author Themiya D Chandraratna
     * @param view
     */
    public void editBio(View view){
        if (editBioState == 1){
            editBioState--;
            bioIc.setVisibility(View.INVISIBLE);
            bio.setEnabled(false);
            bio.clearFocus();
        }
        else{
            editBioState++;
            bioIc.setVisibility(View.VISIBLE);
            bio.setEnabled(true);
        }
    }

    /**
     * Profile Thread
     * @author Themiya D Chandraratna
     */
    class UpdateProfile extends AsyncTask<String, Void, Void> {
        private RequestHandler handler;

        public UpdateProfile() {
            handler = new HTTPHandler();
        }

        public UpdateProfile(RequestHandler handler) {
            this.handler = handler;
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                boolean isUpdated = handler.updateProfile(params[0], params[1], params[2], params[3], params[4], params[5]);
                if (isUpdated) {
                    msg = "Profile changes saved!";
                }
                else {
                    msg = "Sorry changes to the profile were not changed!";
                }
            }
            catch (Exception e) {
                msg = "Database Connection Error!";
                //Log.d("DatabaseConnection", "ProfileUpdateFailed");
            }
            return null;
        }
        protected void onPostExecute(Void result) {
            Toast.makeText(Profile.this, msg, Toast.LENGTH_LONG).show();
        }
    }

    //go back to movie search
    public void goHome(View view) {
        if (!String.valueOf(name.getText()).equals("Enter your name")) {
            MainActivity.myUser.username = String.valueOf(name.getText());
        }
        if (!String.valueOf(major.getText()).equals("Enter your major")) {
            MainActivity.myUser.major = String.valueOf(major.getText());
        }
        if (!String.valueOf(year.getText()).equals("Enter your year")) {
            MainActivity.myUser.year = String.valueOf(year.getText());
        }
        if (!String.valueOf(bio.getText()).equals("Enter your description")) {
            MainActivity.myUser.bio = String.valueOf(bio.getText());
        }
        //Profile Values
        String email = MainActivity.myUser.email;
        String username = MainActivity.myUser.username;
        String major = MainActivity.myUser.major;
        String year = MainActivity.myUser.year;
        String bio = MainActivity.myUser.bio;
        String imgID = MainActivity.myUser.img_id;
        //Push Values through Rest
        new UpdateProfile().execute(email, username, major, year, bio, imgID);
        //<--------->
        //Go to Movie Search
        Intent i = new Intent(getApplicationContext(), MovieSearch.class);
        startActivity(i);
    }
}
