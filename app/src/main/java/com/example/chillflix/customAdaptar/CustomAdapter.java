package com.example.chillflix.customAdaptar;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chillflix.Model.movie;
import com.example.chillflix.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    ArrayList<movie> movieArrayList;
    public MovieItemClicked movieItemClicked;



    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;
        public ViewHolder(View view) {


            super(view);

            img=(ImageView) view.findViewById(R.id.img_movie);
            // Define click listener for the ViewHolder's View
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int x=getAdapterPosition();
                    movieItemClicked.onItemClicked(movieArrayList.get(x));
                }
            });

        }


    }


    public CustomAdapter(ArrayList<movie> movieArrayList,MovieItemClicked movieItemClicked ) {
        this.movieArrayList = movieArrayList;
        this.movieItemClicked=movieItemClicked;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        movie current=movieArrayList.get(position);

        Picasso.get().load(current.getImgUrl()).placeholder(R.drawable.place).into(viewHolder.img);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

}


