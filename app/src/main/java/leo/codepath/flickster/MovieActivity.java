package leo.codepath.flickster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import leo.codepath.flickster.adapters.MoviesAdapter;
import leo.codepath.flickster.models.Movie;

public class MovieActivity extends AppCompatActivity {

private static final String MOVIE_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    List<Movie> movies;

    /**
     *     Add RecyclerView support library to the gradle build file --DONE
     *     Define a model class to use as the data source -- DONE --MOVIE CLASS
     *     Add a RecyclerView to your activity to display the items -- DONE -- ADDED THE RVMOVIE
     *     Create a custom row layout XML file to visualize the item -- DONE  -- ITEM_MOVIE
     *     Create a RecyclerView.Adapter and ViewHolder to render the item -- DONE
     *     Bind the adapter to the data source to populate the RecyclerView -- DONE
     */



    //here we have a recycler view attached to an adapter
    //the adapter is the one responsible for showing each individual item
    //which the recycler holds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set the layout of the Content view equal to the activity_movie
        //    we are basically calling the Activity_movie Layout which is an XML file
        //the activity_movie.xml is the file containing the Recycler view(rvMovie)
        setContentView(R.layout.activity_movie);

        //the ACTIVITY CLASS  is the class where we make use of methods such as:
        // the creation of the app
        // the action when we STOP the app
        // the actions set for OnRestart


        //because we have set the Content view now it would be useful to call the RecyclerViewer
        RecyclerView rvMovies = findViewById(R.id.rvMovies);

        //initializing the movies Array
        movies = new ArrayList<>();


        //we are initializing a variable form our own CLASS MOVIE_ADAPTER
        //it will take a Context
        //it will also take the Movie_Array
        //the Movie Activity class is the context being passed to the movie adapter class
        final MoviesAdapter adapter = new MoviesAdapter(this,movies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvMovies.setAdapter(adapter);


        AsyncHttpClient client = new AsyncHttpClient();
        //gives us back data in JSON format
        //making a network request on the URL, we want to catch and act upon that result
        client.get(MOVIE_URL, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray movieJsonArray = response.getJSONArray("results");
                    movies.addAll(Movie.fromJsonArray(movieJsonArray));
                    adapter.notifyDataSetChanged();

                    Log.d("smile",movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }
}
