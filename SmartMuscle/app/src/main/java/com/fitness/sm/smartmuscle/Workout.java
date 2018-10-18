package com.fitness.sm.smartmuscle;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Workout {
    private static Workout sWorkout;
    private List<String> routine;

    public static Workout get(Context context){
        if (sWorkout == null){sWorkout= new Workout(context);}
        return sWorkout;
    }

    private Workout(Context context){
        routine = new ArrayList<>();
        for(int i=1; i<9;i++){
            Log.d("FRAG_INIT: ","TEMP ARRAY INDEX: "+i);
            routine.add("Workout" + i);
        }
    }

    public List<String> getRoutine(){return routine;}
    public String getExercise(int index){
        if (index > 0 && index < routine.size()){return routine.get(index);}
        else{return null;}
    }
}
