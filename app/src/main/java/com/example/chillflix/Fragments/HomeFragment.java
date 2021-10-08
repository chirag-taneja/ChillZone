package com.example.chillflix.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.chillflix.AllMoviesActivity;
import com.example.chillflix.Asycronus.Async;
import com.example.chillflix.Asycronus.movieKeyAsync;
import com.example.chillflix.ItemClick.itemClick;
import com.example.chillflix.Model.movie;
import com.example.chillflix.Model.movieKey;
import com.example.chillflix.R;
import com.example.chillflix.Vid;
import com.example.chillflix.controller.MySingleton;
import com.example.chillflix.customAdaptar.CustomAdapter;
import com.example.chillflix.customAdaptar.MovieItemClicked;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements MovieItemClicked {
private ProgressBar pb;
public ArrayList<movie> movies=new ArrayList<>();
    private RecyclerView rv_trends;
private RecyclerView rv_popular;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        Button AllMoviesButton=view.findViewById(R.id.AllMovies_btn);
        pb=view.findViewById(R.id.pb1);
        pb.setVisibility(View.VISIBLE);
        rv_trends=view.findViewById(R.id.rv_trend);
        Button btn=view.findViewById(R.id.fast_play_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String url="https://www.youtube.com/watch?v=940zZ45Ldoc";
                Intent intent=new Intent(getContext(), Vid.class);
                intent.putExtra("key","a8Pj1-KtzNA");
                startActivity(intent);
            }
        });
        rv_trends.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rv_popular=view.findViewById(R.id.rv_popular);
        rv_popular.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        getMovie("https://api.themoviedb.org/3/movie/top_rated?api_key=ef2c21a712008fc6319159a008e0f059",new Async() {

            @Override
            public void process_finished(ArrayList<movie> movieArrayList) {
                CustomAdapter customAdapter=new CustomAdapter(movieArrayList,HomeFragment.this);
                rv_popular.setAdapter(customAdapter);
                pb.setVisibility(View.INVISIBLE);
            }
        });
        getMovie("https://api.themoviedb.org/3/movie/popular?api_key=9646aabddf1c13170f2911b3fee399bd",new Async() {

            @Override
            public void process_finished(ArrayList<movie> movieArrayList) {
                CustomAdapter customAdapter=new CustomAdapter(movieArrayList,HomeFragment.this);
                rv_trends.setAdapter(customAdapter);
                pb.setVisibility(View.INVISIBLE);
            }
        });

        AllMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), AllMoviesActivity.class);
                startActivity(intent);
            }
        });
        RecyclerView rv_us=view.findViewById(R.id.rv_us);
        rv_us.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));



        getMovie("\n" +
                "https://api.themoviedb.org/3/movie/popular?api_key=9646aabddf1c13170f2911b3fee399bd&region=in&page=2&language=hi",new Async() {

            @Override
            public void process_finished(ArrayList<movie> movieArrayList) {
                CustomAdapter customAdapter=new CustomAdapter(movieArrayList,HomeFragment.this);
                rv_us.setAdapter(customAdapter);

            }
        });





        return view;
    }

    public void getMovie(String url,Async callback) {
        ArrayList<movie> movieArrayList = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.getJSONArray("results");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (jsonArray != null) {
                            try {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String title = (String) object.get("original_title");
                                    String image = "https://image.tmdb.org/t/p/w500" + object.get("poster_path");
                                    String lang = (String) object.get("original_language");
                                    long id = object.getInt("id");
                                    title.replace("'", "");
                                    movie obj = new movie(id, title, lang, image);

                                    movieArrayList.add(obj);
                                }
                                if (callback != null) callback.process_finished(movieArrayList);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                }, new Response.ErrorListener() {


                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", "onErrorResponse: ");
                    }
                });
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }


    @Override
    public void onItemClicked(movie obj) {
       itemClick.movieItemClick((int) obj.getMovieId(), getContext(), new movieKeyAsync() {
           @Override
           public void process_finished(movieKey Key) {
               Intent intent=new Intent(getContext(), Vid.class);
               intent.putExtra("key",Key.getKey());
               startActivity(intent);

           }
       });
        Intent intent=new Intent(getContext(), Vid.class);
        intent.putExtra("key","mnZeRCrMRFg");
        startActivity(intent);
    }
}
