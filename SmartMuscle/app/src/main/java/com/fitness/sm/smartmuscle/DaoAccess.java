package com.fitness.sm.smartmuscle;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DaoAccess {
    @Insert
    void insertSingleExercise(ExerciseDBObject ex);

    @Insert
    void insertExercises(List<ExerciseDBObject> exList);

    @Query("SELECT * FROM ExerciseDBObject")
    List<ExerciseDBObject> fetchAllExercises();

    @Update
    void updateExercise(ExerciseDBObject ex);

    @Update
    void updateExercises(List<ExerciseDBObject> ex);
}

