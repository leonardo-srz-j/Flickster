package leo.codepath.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

//  THIS CLASS IS THE DATA COLLECTOR, IT CONNECTS TO THE SOURCE


@Parcel
public class Movie {
    String posterPath;
    String title;
    String overview;
    String backdropPath;
    int movieID;
    String movieRating;


//make this constructor throw an exception so that whoever calls this will have to deal with it
    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        backdropPath = jsonObject.getString("backdrop_path");
        movieID = jsonObject.getInt("id");
        movieRating = jsonObject.getString("vote_average");



    }

    //empty constructor is needed by the parceler Library
    public Movie() {
    }

    //it takes a json array
    //and we are gonna iterate through the array and generate a list of movie
    //the reason it is static is because we do not want to have to create
    //an instance of the movie class in order to use this method
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException{
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i< movieJsonArray.length();i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getPosterPath() {
        //%s means that whatever we put in as the second element of the formatter will take its place
        //perfect for the URL
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getMovieRating() { return movieRating; }

    public int getMovieID() { return movieID; }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}


