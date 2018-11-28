package com.fitness.sm.smartmuscle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ExerciseFragmentContainer extends Fragment {

    private TextView heading;
    private TextView details;
    private String headingText;
    private String detailsText;
    private Workout workout;
    private Exercise exercise;
    private Button fwd_btn;
    private Button back_btn;
    private FragmentManager fm;
    private List<ExerciseFragment> routine = new ArrayList<>();
    private int index=0;


    public ExerciseFragmentContainer() {
        // Required empty public constructor
        headingText = null;
    }
    /*
    @SuppressLint("ValidFragment")
    public ExerciseFragmentContainer(String name){
        headingText = name;
    }*/

    public Workout getWorkout() {return workout;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_container, container, false);
        fm = getChildFragmentManager();
        heading = (TextView) view.findViewById(R.id.exercise_name);
        details = (TextView) view.findViewById(R.id.exercise_head_details);
        heading.setText(headingText);
        details.setText(detailsText);

        // both buttons need better visuals from out assets team
        // use https://developer.android.com/guide/topics/ui/controls/button#CustomBackground for reference
        fwd_btn = (Button) view.findViewById(R.id.exercise_fwd_btn);
        back_btn = (Button) view.findViewById(R.id.exercise_back_btn);
        fwd_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Change exercise fragment to next in the routine.
                if (index !=routine.size()-1){
                    Log.d("INDEX CHANGE","current index: "+index+" | next index: "+(index+1));
                    setExercise(workout.getExercise(index+1));
                    fm.beginTransaction().replace(R.id.exercise_frame,routine.get(index)).commit();
                    routine.get(index-1).pauseVideo();
                    //routine.get(index).resumeVideo();
                }
                //if last change the btn to non clickable?
            }});

        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Change exercise fragment to previous in the routine.
                if (index != 0){
                    Log.d("INDEX CHANGE","current index: "+index+" | next index: "+(index-1));
                    setExercise(workout.getExercise(index-1));
                    fm.beginTransaction().replace(R.id.exercise_frame,routine.get(index)).commit();
                    routine.get(index+1).pauseVideo();
                    //routine.get(index).resumeVideo();
                }
                // if first change btn to non clickable
            }});
        Log.d("EFC_OnCreate","Routine size: "+routine.size());
        fm.beginTransaction().add(R.id.exercise_frame,routine.get(index)).commit();
        return view;
    }
    private void updateHeading() {
        if(heading!= null){
            heading.setText(headingText);
            details.setText(detailsText);
        }
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
        Log.d("SET_EXERCISE","exercise set to: "+exercise.toString());
        index = workout.getExerciseIndex(exercise);
        headingText = exercise.getName();
        detailsText = "Reps: " + exercise.getReps() + " | Sets: " + exercise.getSets();
        updateHeading();

    }

    private void setExercise(int i) {
        if (exercise != null){setExercise(workout.getExercise(i));}
    }

    public void setWorkout(Workout w){
        Log.i("EFC_SETWORKOUT","Started");
        workout = w;

        if (routine != null){
            routine.clear();
        }
        List<Exercise> ex = workout.getRoutine();
        for(int i=0;i<ex.size();i++){
            ExerciseFragment tmp_ef = new ExerciseFragment();
            tmp_ef.setExercise(ex.get(i));
            Log.d("EFC_SETWORKOUT", tmp_ef.toString()+" added to routine");
            routine.add(tmp_ef);
        }

        for(ExerciseFragment ef:routine){
            Log.d("EFC Routine",ef.getExercise().getName());
        }

    }




}
