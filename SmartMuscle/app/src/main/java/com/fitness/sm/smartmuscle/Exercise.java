package com.fitness.sm.smartmuscle;

import android.util.Log;

import com.fitness.sm.smartmuscle.helpers.Muscles;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Exercise extends Muscles {


    private String url;
    private List<String> steps;
    private String name;
    private int sets;
    private int reps;
    private int weight;
    private MuscleGroup mg;
    private boolean finished = false;

    Exercise(String n,List<String> s, String u, int st, int rp){
        name = n;
        steps = s;
        url = u;
        sets=st;
        reps=rp;
    }

    Exercise(String n, int i){ //Temporary constructor (JUST TO SET TEST VALUES)
        name = n;
        steps = new ArrayList<>();
        for (int j=0; j<i;j++){
            steps.add("Step "+j);

        }
        url = "ZZ5LpwO-An4";
        sets = i;
        reps = i*2;
    }

    Exercise(ExerciseDBObject edb){
        name = edb.getName();
        url = edb.getUrl();
        sets = edb.getSets();
        reps = edb.getReps();
        weight = edb.getWeight();
        mg = Muscles.getMuscleGroup(edb.getMg_int());
        /*if (edb.getWanted()==1){
            wanted=true;
        }else {
            wanted=false;
        }*/
        steps = new ArrayList<>();
        try {
            JSONArray j_steps = new JSONArray(edb.getSteps());
            for(int i=0; i<j_steps.length();i++){
                steps.add(j_steps.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Exercise_Constructor","Unable to parse JSON Array");
        }
    }

    public void setName(String name) {this.name = name;}

    public void setUrl(String url) {this.url = url;}

    public String getName() {return name;}

    public List<String> getSteps() {return steps;}

    public String getStep(int i){ return steps.get(i);}

    public String getUrl() {return url;}

    public int getSets() {return sets;}

    public int getReps() {return reps;}

    public boolean isFinished() {return finished;}

    public void finish(){finished = true;}

    public MuscleGroup getMg() {return mg;}

    public int getWeight() {return weight;}

}
