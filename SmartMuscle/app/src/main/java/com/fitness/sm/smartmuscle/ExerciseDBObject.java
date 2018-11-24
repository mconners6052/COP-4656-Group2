package com.fitness.sm.smartmuscle;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class ExerciseDBObject {
    @NonNull
    @PrimaryKey
    private String url;
    @NonNull
    private String name;
    //@NonNull
    private String steps;
    @NonNull
    private int mg_int;
    @NonNull
    private int sets;
    @NonNull
    private int reps;
    @NonNull
    private int weight;
    @NonNull
    private int wanted;

    @Ignore
    public ExerciseDBObject(){};

    @Ignore
    public ExerciseDBObject(String url, String name, int mg_int){
        this.url = url;
        this.name = name;
        this.mg_int =mg_int;
    }


    public ExerciseDBObject(String url, String name, String steps, int mg_int, int sets, int reps, int weight, int wanted){
        this.name = name;
        this.url =url;
        this.steps =steps;
        this.mg_int =mg_int;
        this.reps = reps;
        this.sets = sets;
        this.weight =weight;
        this.wanted = wanted;
    }

    @Ignore
    public ExerciseDBObject(String url, String name, int mg_int, int sets, int reps, int weight, int wanted){
        this.name = name;
        this.url =url;
        this.mg_int =mg_int;
        this.reps = reps;
        this.sets = sets;
        this.weight =weight;
        this.wanted = wanted;
    }

    @NonNull
    public String getName() {return name;}
    public void setName(@NonNull String name) {this.name = name;}

    @NonNull
    public String getUrl() {return url;}
    public void setUrl(@NonNull String url) {this.url = url;}

    @NonNull
    public String getSteps() {return steps;}
    public void setSteps(@NonNull String steps) {this.steps = steps;}

    @NonNull
    public int getMg_int() {return mg_int;}
    public void setMg_int(@NonNull int mg_int) {this.mg_int = mg_int;}

    @NonNull
    public int getSets() {return sets;}
    public void setSets(@NonNull int sets) {this.sets = sets;}

    @NonNull
    public int getReps() {return reps;}
    public void setReps(@NonNull int reps) {this.reps = reps;}

    @NonNull
    public int getWeight() {return weight;}
    public void setWeight(@NonNull int weight) {this.weight = weight;}

    @NonNull
    public int getWanted() {return wanted;}
    public void setWanted(@NonNull int wanted) {this.wanted = wanted;}
}
