package com.fitness.sm.smartmuscle.helpers;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.fitness.sm.smartmuscle.ExerciseDBObject;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDataOb implements ParentObject {
    private ExerciseDBObject exercise;
    private List<Object> children;

    public ExerciseDataOb(ExerciseDBObject d){
        exercise =d;
        children = new ArrayList<>();
        children.add(exercise);
    }

    public ExerciseDBObject getExercise() {return exercise;}

    @Override
    public List<Object> getChildObjectList() {
        return children;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        children = list;
    }
}
