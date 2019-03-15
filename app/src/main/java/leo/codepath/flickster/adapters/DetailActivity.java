package leo.codepath.flickster.adapters;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;
import leo.codepath.flickster.R;
import leo.codepath.flickster.models.Movie;

/**
 * This Class is our second Activity
 * This class holds all the variables corresponding to the components of
 * the Resource Layout in this case, the Activity_Detail Layout
 * Meaning this class will hold the TvTitle, StarRatings,and TvOverview
 *
 * @author Leonardo Suarez
 *
 */


public class DetailActivity extends YouTubeBaseActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyDpt6Cq7NSZaGiOd2FImHQjeEF0YspRRfc";
    private static final String TRAILER_ID = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    TextView tvTitle;
    RatingBar ratingBar;
    TextView tvOverview;
    Movie movieObject;
    YouTubePlayerView youTubePlayerView;
    int MOVIE_ID;


    /**
     * This method is used ON CREATE, meaning when this activity is
     * called upon it will run this piece of code
     * and it will start setting the class fields to
     * their corresponding Layout components
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final LinearLayout context = findViewById(R.id.activityDetail);
        tvTitle= findViewById(R.id.tvTitle);
        ratingBar = findViewById(R.id.ratingBar);
        tvOverview = findViewById(R.id.tvOverview);
        youTubePlayerView =  findViewById(R.id.player);
        final ImageView poster = findViewById(R.id.ivPoster);

        //our details activity must receive the intent
        //because this will be the second
        //the name indicates the name of the extra
        movieObject = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        //this would be a bit useless now: tvTitle.setText(getIntent().getStringExtra("title"));
        //instead we try
        tvTitle.setText(movieObject.getTitle());
        tvOverview.setText(movieObject.getOverview());
        ratingBar.setRating(Float.parseFloat(movieObject.getMovieRating()));

        MOVIE_ID = movieObject.getMovieID();

        /**
         * A JsonArray is an array of Json Objects
         *
         * We only use index 0, because the search results will return an array of videos
         *      but we only need one so we take only one (at index 0)
         *
         * NOTE: there is a "JSONObject.get()" method it but only returns a JsonObject
         * however, we want the string so we use movieTrailer.getString()
         */
        final AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(TRAILER_ID,MOVIE_ID), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray results = response.getJSONArray("results");
                    if (results.length() == 0) {
                        //String imageURL = movieObject.getPosterPath();
                        //youTubePlayerView.addView(poster);
                        //Glide.with(context).load(imageURL).into( youTubePlayerView);
                        //should i add a container instead of the youtube player and then rotate them?
                        //i tried replacing them but i couldn't
                        return;}// try returning the poster movie; should i use a "fragment"

                    /**
                     * to add
                     * if on youtube good;
                     *      else proceed to the next index
                     *
                     *
                     */

                    JSONObject movieTrailer = results.getJSONObject(0);
                    String trailerKey = movieTrailer.getString("key");
                    initializeYoutubeTrailer(trailerKey);// our new method
                } catch (JSONException e) {
                    e.printStackTrace(); }
            }// end of onSuccess

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable); }
        });
    }//end of OnCreate method




    /**
     * It is better practice to have you own method to initialize the youtube trailer
     * This way if there is any bugs you know which section of your code needs fixing
     * It is also better for organization and clarity
     */
    private void initializeYoutubeTrailer(final String trailerKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("leo", "youtube init success");
                youTubePlayer.cueVideo(trailerKey); }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("leo", "youtube init failure");
            }
        });
    }
}
