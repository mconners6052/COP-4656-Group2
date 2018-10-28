package com.fitness.sm.smartmuscle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.List;

public class ExerciseFragment extends Fragment {
    private Exercise ef_exercise;
    private TextView textV;
    private String text;

    public ExerciseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        textV = view.findViewById(R.id.test_text);
        textV.setText(text);
        return view;
    }

    public void setExercise(Exercise exercise) {
        ef_exercise = exercise;
        text = ef_exercise.getName();
    }

    public Exercise getExercise(){return ef_exercise;}
}
