package com.example.chillflix.Asycronus;

import com.example.chillflix.Model.movie;
import com.example.chillflix.Model.movieKey;

import java.util.ArrayList;

public interface movieKeyAsync {
    public void process_finished(movieKey Key);
}
