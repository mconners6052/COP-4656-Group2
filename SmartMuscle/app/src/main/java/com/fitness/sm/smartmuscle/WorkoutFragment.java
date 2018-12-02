package com.fitness.sm.smartmuscle;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class WorkoutFragment extends Fragment {
    private RecyclerView mWorkoutRecyclerView;
    private WorkoutAdapter mAdapter;
    private Workout workout;
    private ExerciseFragmentContainer efc;

    public WorkoutFragment(){

    }

    /*public WorkoutFragment(){
        workout = Workout.get(getActivity());
        efc = new ExerciseFragmentContainer();
        efc.setWorkout(workout);
    }

   /* public WorkoutFragment(){
        workout = w;

    }*/

    private void refreshWorkout(){
        //create new workout
        // workout = newWorkout;
        efc.setWorkout(workout);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);
        mWorkoutRecyclerView = (RecyclerView)view.findViewById(R.id.workout_recycler);
        mWorkoutRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        if (mAdapter == null) {
            //This would be a good place to initialize the daily workout
            mAdapter = new WorkoutAdapter(workout);
            mWorkoutRecyclerView.setAdapter(mAdapter);
        }else{
            // if the workout needs to be updated this would be a good spot for it
            mWorkoutRecyclerView.setAdapter(mAdapter);
        }
    }

    public void setWorkout(Workout w){workout = w;}
    public void setEfc(ExerciseFragmentContainer efc){
        this.efc = efc;
        workout = efc.getWorkout();
    }

    private class ExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mSets;
        private TextView mReps;
        private Exercise exercise;

        public ExerciseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.workout_list_item, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.exercise);
            mSets = (TextView) itemView.findViewById(R.id.exercise_sets);
            mReps = (TextView) itemView.findViewById(R.id.exercise_reps);
        }

        public void bind(Exercise ex) {
            exercise = ex;
            mTitleTextView.setText(ex.getName());
            mSets.setText("Sets: "+ex.getSets());
            mReps.setText("Reps: "+ex.getReps());

        }

        @Override
        public void onClick(View v) {
            FragmentManager fm = getFragmentManager();
            efc.setExercise(exercise);
            //trigger callback in main to swap to EFC
            fm.beginTransaction().replace(R.id.main_frame, efc).commit();

        }
    }

    private class WorkoutAdapter extends RecyclerView.Adapter<ExerciseHolder> {
        private List<Exercise> exercises;
        public WorkoutAdapter(Workout w) {
            exercises = w.getRoutine();}
        @Override
        public ExerciseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ExerciseHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(ExerciseHolder holder, int position) {
            //Log.d("Recycler: ","onBindViewHolder: called");
            holder.bind(exercises.get(position));
        }
        @Override
        public int getItemCount() {return exercises.size();}
    }




}
