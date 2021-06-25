package com.example.flixter.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.MovieDetailsActivity;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Involves populating data into the item through a holder
    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the VH
        holder.bind(movie);
    }

    // Returns the total count of the items on the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            int radius = 30;
            int margin = 10;

            String imageUrl;

            // if phone is in landscape, then we want to set imageurl = backdrop image
            // else imageurl = poster image

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackdropPath();
            }
            else{
                imageUrl = movie.getPosterPath();
            }

            Glide.with(context)
                    .load(imageUrl)
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .placeholder(R.drawable.flicks_movie_placeholder)
                    .override(200, 300)
                    .into(ivPoster);


            //Glide.with(context).load(movie.getPosterPath()).into(ivPoster);
        }

        @Override
        public void onClick(View v) {
            // collects position
            int position = getAdapterPosition();

            // verifies this position exists
            if (position != RecyclerView.NO_POSITION){

                // collects movie at said position
                Movie movie = movies.get(position);

                // creates intent for new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);

                // serialize movie using parceler
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));

                // show activity
                context.startActivity(intent);
            }
        }
    }


}
