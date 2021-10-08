package com.example.chillflix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.chillflix.Asycronus.Async;
import com.example.chillflix.Asycronus.movieKeyAsync;
import com.example.chillflix.ItemClick.itemClick;
import com.example.chillflix.Model.movieKey;
import com.example.chillflix.customAdaptar.CustomAdapter;
import com.example.chillflix.Model.movie;
import com.example.chillflix.controller.MySingleton;
import com.example.chillflix.customAdaptar.MovieItemClicked;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllMoviesActivity extends AppCompatActivity implements MovieItemClicked {
    int page=1;
    public ArrayList<movie> movies=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_movies);
        getSupportActionBar().hide(); //hide action bar

        RecyclerView rv_all=findViewById(R.id.rv_all);

        rv_all.setLayoutManager(new GridLayoutManager(this,3));
        CustomAdapter ca=new CustomAdapter(movies,this);
        getMovie(new Async() {
            @Override
            public void process_finished(ArrayList<movie> movieArrayList) {


                rv_all.setAdapter(ca);
                rv_all.invalidate();

            }
        },page);
        rv_all.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!rv_all.canScrollVertically(1))
                {
                    page++;
                    getMovie(new Async() {
                        @Override
                        public void process_finished(ArrayList<movie> movieArrayList) {

                        }
                    },page);
                }
                ca.notifyDataSetChanged();

            }
        });


    }


    public void getMovie(Async callback, int pg) {

        ArrayList<movie> movieArrayList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=9646aabddf1c13170f2911b3fee399bd&page="+pg+"&region=in&language=hi";
        System.out.println(url);
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
                                    movies.add(obj);
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
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onItemClicked(movie obj) {
        itemClick.movieItemClick((int) obj.getMovieId(),this, new movieKeyAsync() {
            @Override
            public void process_finished(movieKey Key) {
                Intent intent=new Intent(AllMoviesActivity.this,Vid.class);
                intent.putExtra("key",Key.getKey());
                startActivity(intent);

            }
        });
        Intent intent=new Intent(this, Vid.class);
        intent.putExtra("key","mnZeRCrMRFg");
        startActivity(intent);

    }
}

