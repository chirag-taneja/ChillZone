package com.example.chillflix.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

public class SearchFragment extends Fragment implements MovieItemClicked {
    int page=1;
    public ArrayList<movie> movies=new ArrayList<>();
    private ProgressBar pb;
    String query;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_search,container,false);

        Button Search_btn=view.findViewById(R.id.search_btn);
        TextView searchText=view.findViewById(R.id.tv);
        pb=view.findViewById(R.id.pb);
        RecyclerView rv=view.findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(getContext(),3));
        CustomAdapter ca=new CustomAdapter(movies,SearchFragment.this);
        Search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page=1;
                movies.clear();
                pb.setVisibility(View.VISIBLE);

                getMovie(searchText.getText().toString(),new Async() {
                    @Override
                    public void process_finished(ArrayList<movie> movieArrayList) {


                        rv.setAdapter(ca);
                        rv.invalidate();
                        pb.setVisibility(View.INVISIBLE);
                    }
                },page);
            }
        });
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!rv.canScrollVertically(1))
                {
                    page++;
                    getMovie(searchText.getText().toString(), new Async() {
                        @Override
                        public void process_finished(ArrayList<movie> movieArrayList) {

                        }
                    },page);
                }
                ca.notifyDataSetChanged();
            }
        });



        return  view;
    }


    public void getMovie(String query,Async callback,int pg) {

            ArrayList<movie> movieArrayList = new ArrayList<>();
            String url = "https://api.themoviedb.org/3/search/movie?api_key=9646aabddf1c13170f2911b3fee399bd&page="+pg+"&query=" + query ;

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

