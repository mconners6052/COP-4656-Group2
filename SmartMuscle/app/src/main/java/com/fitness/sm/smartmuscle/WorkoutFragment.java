package com.fitness.sm.smartmuscle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WorkoutFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private WorkoutAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);
        mCrimeRecyclerView = (RecyclerView)view.findViewById(R.id.workout_recycler);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        Workout workout = Workout.get(getActivity());
        List<String> exercises = workout.getRoutine();
        mAdapter = new WorkoutAdapter(exercises);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    private class ExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private String exercise;
        private boolean expanded = false;

        public ExerciseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.workout_list_item, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.exercise);
        }

        public void bind(String name) {
            exercise = name;
            mTitleTextView.setText(exercise);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), exercise + " clicked!", Toast.LENGTH_SHORT).show();
            /*LinearLayout exexpan = (LinearLayout)v.findViewById(R.id.exercise_expanded);
            if (expanded) {
                exexpan.setVisibility(View.INVISIBLE);
                expanded = false;
            } else{
                exexpan.setVisibility(View.VISIBLE);
                expanded = true;
            }*/

        }
    }

    private class WorkoutAdapter extends RecyclerView.Adapter<ExerciseHolder> {
        private List<String> exercises;
        public WorkoutAdapter(List<String> ex) {
            exercises = ex;}
        @Override
        public ExerciseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ExerciseHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(ExerciseHolder holder, int position) {
            Log.d("Recycler: ","onBindViewHolder: called");
            String name = exercises.get(position);
            holder.bind(name);
        }
        @Override
        public int getItemCount() {return exercises.size();}
    }



}
