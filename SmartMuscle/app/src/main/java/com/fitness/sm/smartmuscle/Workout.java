package com.fitness.sm.smartmuscle;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Workout{
    private static Workout sWorkout;
    private List<Exercise> routine;

    public static Workout get(Context context){
        if (sWorkout == null){sWorkout= new Workout(context);}
        return sWorkout;
    }

    private Workout(Context context){ //Temporary constructor (JUST TO SET TEST VALUES)
        routine = new ArrayList<>();
        for(int i=1; i<13;i++){
            Log.d("FRAG_INIT: ","TEMP Exercise Index: "+i);
            routine.add(new Exercise("Exercise "+i,i+1));
        }
    }

    public List<Exercise> getRoutine(){return routine;}
    public Exercise getExercise(int index){
        if (index >= 0 && index < routine.size()){return routine.get(index);}
        else{return null;}
    }
    public int getExerciseIndex(Exercise ex){
        int i =0;
        for (Exercise e:routine){
            if (e == ex){
                Log.d("GET_EXERCISE_IDEX: ",ex.getName() + " | index: " + i); //just for testing purposes
                return i;
            }else{
                i++;
            }
        }
        return -1;
    }
}
