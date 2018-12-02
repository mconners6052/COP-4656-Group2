package com.fitness.sm.smartmuscle;

import android.content.Context;
import android.util.Log;
import com.fitness.sm.smartmuscle.helpers.UpdateHandler;
import com.fitness.sm.smartmuscle.helpers.Muscles;
import com.fitness.sm.smartmuscle.helpers.UpdateEvent;
import com.fitness.sm.smartmuscle.helpers.UpdateListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Workout extends Muscles {
    private static Workout sWorkout;
    private static List<Exercise> routine;
    private int disLikedExercise = 0;
    private static Context context;
    private static ArrayList<UpdateListener> listeners = new ArrayList<>();
    private static Date last_refresh;



    public synchronized static Workout get(Context context){
        if (sWorkout == null){sWorkout= new Workout(context);}
        return sWorkout;
    }

   /*private Workout(Context context){ //Temporary constructor (JUST TO SET TEST VALUES)
        //updateWorkout();
        routine = new ArrayList<>();
        for(int i=1; i<13;i++){
            Log.d("FRAG_INIT: ","TEMP Exercise Index: "+i);
            routine.add(new Exercise("Exercise "+i,i+1));
        }
    }
   */
   private Workout(Context context){ //Temporary constructor (Getting Values from DB)
       this.context = context;
       updateWorkout();
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

    public static void updateWorkout() {
        init_workout("Static Workout");
    }
    public static void updateWorkout(String caller){
       init_workout(caller);
    }

    private static void init_workout(String caller){
        //THIS WHERE THE WORKOUTS ARE MADE.
        // Look at an exercise to get the muscle group than use that to update the muscle group
        final ExerciseDB dbInstance;
        final String identifier = caller;
        dbInstance = ExerciseDB.getInstance(context);
        routine = null;
        routine = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ExerciseDBObject> exercises = new ArrayList<>();
                exercises = dbInstance.dao().fetchAllExercises();
                for(ExerciseDBObject ob: exercises){
                    routine.add(new Exercise(ob));
                    Log.d("DataRetrieved", ob.getName());
                }

                disbatchEvent(new UpdateEvent(identifier));
            }
        }).start();

    }

    //these functions are to handle and distribute update events

    public void addUpdateListener(UpdateHandler handler) {
        UpdateListener l = new UpdateListener(handler);
        listeners.add(l);
    }

    private static void disbatchEvent(UpdateEvent e) {
        final UpdateEvent event = e;
        for (UpdateListener l : listeners) {
            UpdateHandler eh = l.getHandler();
            eh.onUpdate(event);
        }
    }

    //This method allows Fragments (EFC and WorkoutFragment) to request
    public void requestUpdate(String identifier){
       disbatchEvent(new UpdateEvent(identifier,"WORKOUT_REQUEST"));
    }


}
