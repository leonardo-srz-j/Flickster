package leo.codepath.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import leo.codepath.flickster.R;
import leo.codepath.flickster.models.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MoviesAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    //inflates the item movie layout
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Log.d("smile","onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return (new ViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Log.d("smile","onBindViewHolder" + position);

        Movie movie = movies.get(position);
        //bind the movie data into the view holder
        viewHolder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(View itemView){
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);


        }


        //this is what is adding all the material to the view holder
        public void bind(Movie movie) {
            tvTitle.setText((movie.getTitle()));
            tvOverview.setText(movie.getOverview());

            String imageURL = movie.getPosterPath();


            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageURL = movie.getBackdropPath();
            }
            Glide.with(context).load(imageURL).into(ivPoster);

        }
    }
}
