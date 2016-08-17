package com.trollema.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.os.AsyncTask;
import java.util.ArrayList;
import android.widget.EditText;
import com.trollema.controllers.HTTPHandler;
import com.trollema.controllers.MovieDataController;
import java.util.Random;
import android.util.Log;
import com.trollema.appdata.models.Rating;
import com.trollema.controllers.RequestHandler;
import com.trollema.controllers.StringValidator;

/**
 * Movie Activity
 * Movie Search Screen
 * @author Themiya D Chandraratna
 */
public class MovieSearch extends AppCompatActivity {

    TextView displayText;
    Button profile_button;
    Button account_button;
    ListView search_results;
    ArrayAdapter adapter;
    EditText searchBar;
    Rating[]ratingData;
    Rating[]recommendations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
        profile_button = (Button) findViewById(R.id.profile_button);
        displayText = (TextView) findViewById(R.id.display);
        account_button = (Button) findViewById(R.id.login_button);
        search_results = (ListView) findViewById(R.id.searchResults);
        searchBar = (EditText) findViewById(R.id.searchBar);
        //If no user is found -- hide profile and display text
        if (MainActivity.myUser.activeCode.equals("0")) {
           profile_button.setVisibility(View.INVISIBLE);
            displayText.setVisibility(View.INVISIBLE);
        }
        //Otherwise show
        else {
            profile_button.setVisibility(View.VISIBLE);
            String userName = MainActivity.myUser.username;
            if (userName.equals("default")) {
                String[]email = MainActivity.myUser.email.split("@");
                userName = email[0];
            }
            displayText.setText("Welcome "+userName);
            displayText.setVisibility(View.VISIBLE);
            account_button.setText("LOGOUT");
        }
    }

//    @Override
//    /**
//     * Click Functionality
//     * @author Themiya D Chandraratna
//     */
//    public void onClick(View view) {
//        Log.d("Hello", "Hello");
//        if (view.getId() == R.id.searchRequest) {
//            populatedListView(view);
//        }
//    }

    @Override
    public void onBackPressed() {
    }

    public void searchButtonPress(View view){
        populatedListView(view);
    }

    class MyLogout extends AsyncTask<Void, Void, Void> {
        private RequestHandler handler;

        public MyLogout() {
            handler = new HTTPHandler();
        }

        public MyLogout(RequestHandler handler) {
            this.handler = handler;
        }

        @Override
        protected Void doInBackground(Void... inputs) {
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
            Toast.makeText(MovieSearch.this, "Sucess!", Toast.LENGTH_LONG).show();
        }
    }

    private void populatedListView(View view){
        String searchText = String.valueOf(searchBar.getText());

        //rating data
        Rating movie1 = new Rating("computer science","Zootopia", 10, 4);
        Rating movie2 = new Rating("aerospace engineering","Zootopia", 20, 4);
        Rating movie3 = new Rating("civil engineering","Zootopia", 11, 4);
        Rating movie4 = new Rating("computer science","Allegiant", 15, 4);
        Rating movie5 = new Rating("aerospace engineering","Allegiant", 17, 4);
        Rating movie6 = new Rating("civil engineering","Allegiant", 18, 4);
        Rating movie7 = new Rating("computer science","Miracles from Heaven", 11, 4);
        Rating movie8 = new Rating("aerospace engineering","Miracles from Heaven", 20, 4);
        Rating movie9 = new Rating("civil engineering","Miracles from Heaven", 10, 4);
        Rating movie10 = new Rating("civil engineering","10 Cloverfield Lane", 18, 4);
        //load array
        ratingData = new Rating[10];
        ratingData[0] = movie1;
        ratingData[1] = movie2;
        ratingData[2] = movie3;
        ratingData[3] = movie4;
        ratingData[4] = movie5;
        ratingData[5] = movie6;
        ratingData[6] = movie7;
        ratingData[7] = movie8;
        ratingData[8] = movie9;
        ratingData[9] = movie10;

        if (searchText.equals("*")) {
            new RecommendationAction().execute();
        }

        if (!searchText.isEmpty()&&!searchText.equals("*")) {
            new MovieSearchAction().execute(searchText);
        }
        else {
            if (adapter!=null&&!searchText.equals("*")) {
                adapter.clear();
                search_results.setAdapter(adapter);
            }
        }
    }

    //go to accounts screen
    public void goToAccounts(View view){
        if (String.valueOf(account_button.getText()).equals("LOGOUT")) {
            new MyLogout().execute();
            profile_button.setVisibility(View.INVISIBLE);
            displayText.setVisibility(View.INVISIBLE);
            account_button.setText("ACCOUNTS");
        }
        else {
            Intent i = new Intent(getApplicationContext(), Accounts.class);
            startActivity(i);
        }
    }

    /**
     * MovieSearch Thread
     * @author Themiya D Chandraratna
     */
    class MovieSearchAction extends AsyncTask<String, Void, Void> {
        private RequestHandler handler;

        public MovieSearchAction() {
            handler = new HTTPHandler();
        }

        public MovieSearchAction(RequestHandler handler) {
            this.handler = handler;
        }

        @Override
        protected Void doInBackground(String ... inputs) {
            ArrayList<String>movies = MovieDataController.getMovieTitles(inputs[0], handler);
            adapter = new ArrayAdapter(MovieSearch.this, R.layout.activity_listview, movies);
            return null;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(MovieSearch.this, "Please Wait...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(MovieSearch.this, "Search Results Shown", Toast.LENGTH_LONG).show();
            search_results.setAdapter(adapter);
        }
    }

    /**
     * Recommendations Thread
     * @author Themiya D Chandraratna
     */
    class RecommendationAction extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void ... inputs) {
            //get movie recommendations by major
            ArrayList<Rating>moviesByMajor = new ArrayList<>();
            if (StringValidator.isMajor(MainActivity.myUser.major)){
                for (Rating givenRating: ratingData) {
                    if (givenRating.major.equals(MainActivity.myUser.major)) {
                        moviesByMajor.add(givenRating);
                    }
                }
            }
            //sort movie recommendations
            recommendations = new Rating[moviesByMajor.size()];
            int rCounter = 0;
            Rating recommendation = null;
            double bestMovie = -1.0;
            int counter = 0;
            while(!moviesByMajor.isEmpty()) {
                if (bestMovie < 0) {
                    recommendation = moviesByMajor.get(counter);
                    bestMovie = recommendation.avg_rating;
                    counter++;
                }
                else {
                    if (counter<moviesByMajor.size()) {
                        if (moviesByMajor.get(counter).avg_rating > bestMovie) {
                            recommendation = moviesByMajor.get(counter);
                            bestMovie = recommendation.avg_rating;
                            counter++;
                        }
                        else {
                            counter++;
                        }
                    }
                    else {
                        moviesByMajor.remove(recommendation);
                        recommendations[rCounter] = recommendation;
                        rCounter++;
                        counter = 0;
                        bestMovie = -1.0;
                    }
                }
            }
            ArrayList<String>myRecommendation = new ArrayList<>();
            for (Rating givenRating : recommendations) {
                myRecommendation.add(givenRating.title);
            }
            adapter = new ArrayAdapter(MovieSearch.this, R.layout.activity_listview, myRecommendation);
            return null;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(MovieSearch.this, "Please Wait...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (recommendations != null && recommendations.length > 0) {
                Toast.makeText(MovieSearch.this, "Recommendations Shown", Toast.LENGTH_LONG).show();
                search_results.setAdapter(adapter);
            }
            else {
                Toast.makeText(MovieSearch.this, "No recommendations to show*", Toast.LENGTH_LONG).show();
            }
        }
    }

    //go to profile screen
    public void goToProfile(View view){
        Intent i = new Intent(getApplicationContext(), Profile.class);
        startActivity(i);
    }

    public void goToRater(View view){
        Intent intent = new Intent(this, RatingActivity.class);
        startActivity(intent);
    }

    //Detect User leave
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.d("Left the application", "Bye!");
    }
}
