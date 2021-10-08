package com.example.chillflix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.PlayerUiController;

public class Vid extends AppCompatActivity {
String key="";
YouTubePlayerView youTubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vid);
        getSupportActionBar().hide(); //hide action bar
        key=getIntent().getStringExtra("key");
        Log.d("key", "onCreate: "+key);


        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.enterFullScreen();
        youTubePlayerView.getPlayerUiController();
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                String videoId = key;
                youTubePlayer.loadVideo(videoId, 0);
            }


        });


    }

    @Override
    protected void onDestroy() {
        key="";
        youTubePlayerView.release();
        super.onDestroy();
    }
}