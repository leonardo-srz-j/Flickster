package leo.codepath.flickster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.List;

import leo.codepath.flickster.R;
import leo.codepath.flickster.models.Movie;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{

    Context context;//the main layout
    List<Movie> movies;//individual movie item in the layout

    //movie adapter constructor takes the context which is the AMin Movie Activity
    //and it will also take the list of movies that we need to put into the container
    public MoviesAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }


    @NonNull
    @Override
    //On Create we Create a ViewHolder
    //HOLDS the item movie layout; check out the fourth line
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Log.d("smile","onCreateViewHolder");

        //this is the view taking in the LayoutInflater
        //it inflates the current layout called "context" which is the Main Activity
        //the Main Activity holds the container layout
        //it inflates it with another layout. in this case, it is the layout that holds individual movies

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



    //NEW CLASS
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        RelativeLayout movieContainer;

        //Constructor for ViewHolder
        public ViewHolder(View itemView){
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            movieContainer = itemView.findViewById(R.id.movieContainer);
        }

        //method for the class View Holder
        //this is what is adding all the material to the view holder
        public void bind(final Movie movie) {
            tvTitle.setText((movie.getTitle()));
            tvOverview.setText(movie.getOverview());
            String imageURL = movie.getPosterPath();

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageURL = movie.getBackdropPath();
            }
            int radius = 30;
            int margin = 10;
            Glide.with(context).load(imageURL).into(ivPoster);//.transform(new RoundedCornersTransformation(radius, margin));

            /**
             * movieContainer is the Parent Layout for the item_movie Layout
             *      -->Relative Layout id = movieContainer
             * Therefore, here we are adding a setOnClickListener for every individual movie
             */
            movieContainer.setOnClickListener(new View.OnClickListener() {

                /**
                 * onClick we create an Intent
                 * An Intent is used to link two activities
                 *  First intent parameter,Context is the MovieActivity class
                 *  Second intent parameter, is the DetailActivity class
                 * <p>
                 * This creates the link between the two and allows for the
                 * DetailActivity class to get this intent and all its attributes
                 *
                 * Our intent is two connect the two
                 * and through the extras we add individual items?
                 *
                 * Intent takes the object I'm passing in and breaks it down into components
                 * to reconstruct on the receiving end, the Detail_Activity
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"clicked title " + movie.getTitle(), Toast.LENGTH_LONG).show();
                    Intent i = new Intent(context,DetailActivity.class);

                    //you put an extra object into the intent, and it must have a name
                    //notice that this is wrapped so we must unwrap when we try to use it
                    i.putExtra("movie" , Parcels.wrap(movie));
                    context.startActivity(i);

                }
            });
        }



    }
}
