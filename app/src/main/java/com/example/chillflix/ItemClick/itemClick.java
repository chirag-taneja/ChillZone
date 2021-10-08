package com.example.chillflix.ItemClick;

import android.app.Application;
import android.content.Context;
import android.graphics.Movie;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.chillflix.Asycronus.movieKeyAsync;
import com.example.chillflix.Model.movieKey;
import com.example.chillflix.controller.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class itemClick {
    public static movieKey movieItemClick(int id, Context context, movieKeyAsync callback) {
        movieKey key_obj = new movieKey();

        String url = "https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=9646aabddf1c13170f2911b3fee399bd&append_to_response=videos";
        System.out.println(url);

        //calling for key


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray=null;
                try {{
                    jsonArray = response.getJSONArray("results");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    System.out.println(jsonObject.toString());
                    String key = jsonObject.getString("key");
                    movieKey Key = new movieKey(key);
                if (callback!=null ) callback.process_finished(Key);}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
        return key_obj;
    }
}


