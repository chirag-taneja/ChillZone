package com.example.chillflix.Asycronus;

import com.example.chillflix.Model.movie;

import java.util.ArrayList;

public interface Async {
    public void process_finished(ArrayList<movie> movieArrayList);
}
