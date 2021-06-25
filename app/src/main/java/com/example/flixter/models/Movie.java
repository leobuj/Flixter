package com.example.flixter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;


// This file is responsible for using the movies API and
// acccessing the data from the internet. It collects the
// address of the poster and backdrop via a string, and
// a description of the movie and its title via a string.

@Parcel // Allows class to be Parcelable
public class Movie {

    String backdropPath;
    String posterPath;
    String overview;
    String title;
    String release_date;
    Double voteAverage;

    public Movie(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        voteAverage = jsonObject.getDouble("vote_average");
        release_date = jsonObject.getString("release_date");
    }

    public Movie(){
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {

        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++){

            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;

    }

    public String getPosterPath() {
        return String.format( "https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath(){
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getOverview() {
        return overview;
    }

    public String getTitle() {
        return title;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    /// Ambitious for the movie description stuff
    //release date
    public String getDate(){
        return release_date;
    }
}
