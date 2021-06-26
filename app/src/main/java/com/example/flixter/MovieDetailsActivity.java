package com.example.flixter;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends AppCompatActivity {

    // Movie to be displayed
    Movie movie;

    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView detailsPoster;
    ImageView iconMovie;

    //Ambitious extra stuff
    TextView release_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);        //Sets the layout to activity_movie_details

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        detailsPoster = (ImageView) findViewById(R.id.detailsPoster);
        iconMovie = (ImageView) findViewById(R.id.imageView3);
        release_date = (TextView) findViewById(R.id.releaseDate);

        // unwrap the movie passed via intent using its simple name as key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // sets the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvOverview.setMovementMethod(new ScrollingMovementMethod());        // Allows text to be scrollable
        release_date.setText(movie.getDate());

        ////////////
        detailsPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent creates the "tunnel connection" between MVD and MTA
                Intent i = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                // Intent contains an integer by the name of "movieid"
                i.putExtra("movieid", movie.getId());
                // Starts the activity at the end of the tunnel
                startActivity(i);
            }
        });

        int radius = 30;
        int margin = 5;

        String overviewImageUrl = movie.getBackdropPath();
        Glide.with(this)
                .load(overviewImageUrl)
                .transform(new RoundedCornersTransformation(radius, margin))
                .placeholder(R.drawable.flicks_movie_placeholder)
                .override(300, 400)
                .into(detailsPoster);

        Glide.with(this)
                .load(R.drawable.epic_movie)
                .into(iconMovie);

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);

    }
}