package com.fitness.sm.smartmuscle;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Workout extends Muscles{
    private static Workout sWorkout;
    private List<Exercise> routine;
    private int disLikedExercise = 0;


    public static Workout get(Context context){
        if (sWorkout == null){sWorkout= new Workout(context);}
        return sWorkout;
    }

    private Workout(Context context){ //Temporary constructor (JUST TO SET TEST VALUES)
        //updateWorkout();
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

    /**
     * Description: Sets an Exercise to Disliked or Liked
     * Input: 
     *  the number of the exercise in the list
     *  the boolean if the exercise is liked or disliked
     * Output: true if there is no error and false if there is an error
     * Limitations: It only works for values less than 64
     */
    public boolean IsLikedExercise(int exerciseNumber,boolean disLiked)
    {
        if(exerciseNumber < 32 && disLiked)
        {
            disLikedExercise |= (1 << exerciseNumber);
            return true;
        }
        else if(exerciseNumber < 32)
        {
            disLikedExercise &= ~(1 << exerciseNumber);
            return true;
        }
        return false;
    }

    /**
     * Description: Tells if an exercise is liked or disliked
     * Input: the number of the exercise in the list
     * Output: 
     *  true if there is exercise is liked
     *  false if the exercise is disliked
     * Limitations: It only works for values less than 32
     */
    public boolean isGoodExercise(int exerciseNumber)
    {
        return (((disLikedExercise >> exerciseNumber)&1) == 0);
    }


    public void updateWorkout(){
        //THIS WHERE THE WORKOUTS ARE MADE.
        // Look at an exercise to get the muscle group than use that to update the muscle group
        if (routine != null){
            // Look at an exercise to get the muscle group than use that to update the muscle group
        }else{
            //
        }
    }
}
