package com.fitness.sm.smartmuscle;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.List;

public class ExerciseFragment extends Fragment {
    private Exercise exercise;
    private RecyclerView mStepRecyclerView;
    private ExerciseFragment.StepAdapter mAdapter;
    private VideoView video;
    private Uri videoURL;
    private SeekBar ratingBar;
    private int difficultyColor = Color.GREEN;
    private static int ORANGE = Color.rgb(255,140,0);
    private CheckBox completed;
    public int difficulty;

    public ExerciseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        mStepRecyclerView = (RecyclerView)view.findViewById(R.id.steps_recycler);
        mStepRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        video = view.findViewById(R.id.video_view);
        video.setVideoURI(videoURL);
        video.start();
        ratingBar = view.findViewById(R.id.seekBar);
        ratingBar.setMax(1);
        ratingBar.setMax(100);
        ratingBar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(difficultyColor, PorterDuff.Mode.SRC_IN));
        ratingBar.getThumb().setColorFilter(new PorterDuffColorFilter(difficultyColor, PorterDuff.Mode.SRC_IN));
        ratingBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    switch (progress){
                        case 10:
                            Toast.makeText(getActivity(),"Extremely Easy",Toast.LENGTH_SHORT).show();
                            break;
                        case 24:
                            difficultyColor = Color.GREEN;
                            Toast.makeText(getActivity(),"Very Easy",Toast.LENGTH_SHORT).show();
                            break;
                        case 25:
                            difficultyColor = Color.YELLOW;
                            break;
                        case 35:
                            Toast.makeText(getActivity(),"Easy",Toast.LENGTH_SHORT).show();
                            break;
                        case 50:
                            difficultyColor = ORANGE;
                            Toast.makeText(getActivity(),"Starting To Feel It!",Toast.LENGTH_SHORT).show();
                            break;
                        case 60:
                            Toast.makeText(getActivity(),"I'm Feeling The Burn!",Toast.LENGTH_SHORT).show();
                            break;
                        case 75:
                            Toast.makeText(getActivity(),"It Was Hard",Toast.LENGTH_SHORT).show();
                            break;
                        case 80:
                            difficultyColor = Color.RED;
                            Toast.makeText(getActivity(),"I Barely Finished",Toast.LENGTH_SHORT).show();
                            break;
                        case 90:
                            Toast.makeText(getActivity(),"Could Not Finish All Sets",Toast.LENGTH_SHORT).show();
                            break;
                        case 100:
                            Toast.makeText(getActivity(),"Could Not Finish A Single Set",Toast.LENGTH_SHORT).show();
                            break;
                    }
                    ratingBar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(difficultyColor, PorterDuff.Mode.SRC_IN));
                    ratingBar.getThumb().setColorFilter(new PorterDuffColorFilter(difficultyColor, PorterDuff.Mode.SRC_IN));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        completed = (CheckBox) view.findViewById(R.id.exercise_completed);
        completed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(completed.isChecked()){
                    exercise.finish();
                }
            }});
        updateUI();
        return view;
    }

    private void updateUI() {
        mAdapter = new StepAdapter(exercise);
        mStepRecyclerView.setAdapter(mAdapter);
    }
    public void setExercise(Exercise ex){
        exercise = ex;
        videoURL = Uri.parse(exercise.getUrl());
        if (mStepRecyclerView != null){updateUI();}
    }
    public Exercise getExercise(){return exercise;}



    private class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mStep;
        private TextView mDescription;
        private Exercise exercise;
        private int startTime;
        private boolean expanded = false;

        public StepHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.exercise_list_item, parent, false));
            itemView.setOnClickListener(this);
            mStep = (TextView) itemView.findViewById(R.id.step_id);
            mDescription = (TextView) itemView.findViewById(R.id.step_description);
        }

        public void bind(Exercise ex, int position) {
            exercise = ex;
            mStep.setText("Step: "+(position+1));
            mDescription.setText(ex.getStep(position));
            startTime = ex.getS_time(position);
        }

        @Override
        public void onClick(View v) {
            //this is where functionality for setting playback for video would go
            Toast.makeText(getActivity(), exercise.getName() + " clicked!", Toast.LENGTH_SHORT).show();
        }
    }


    private class StepAdapter extends RecyclerView.Adapter<ExerciseFragment.StepHolder> {
        private List<String> steps;
        private Exercise ex;
        public StepAdapter(Exercise e) {
            ex=e;
            steps = e.getSteps();
        }

        @Override
        public ExerciseFragment.StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ExerciseFragment.StepHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(ExerciseFragment.StepHolder holder, int position) {
            Log.d("Recycler: ","onBindViewHolder: called");
            holder.bind(ex, position);
        }
        @Override
        public int getItemCount() {return steps.size();}
    }

}