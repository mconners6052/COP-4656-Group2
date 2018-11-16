package com.fitness.sm.smartmuscle;

import java.util.ArrayList;
import java.util.List;

public class Exercise extends Muscles{

    private List<String> steps;
    private List<Integer> s_times;
    private String name;
    private String url;
    private int sets;
    private int reps;
    private int weight;
    private boolean finished = false;
    private MuscleGroup mg;

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
        s_times = new ArrayList<>();
        for (int j=0; j<i;j++){
            steps.add("Step "+j);
            s_times.add(j*5);

        }
        url = "ZZ5LpwO-An4";
        sets = i;
        reps = i*2;
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

    public Integer getS_time(int i) {return s_times.get(i);}
}
