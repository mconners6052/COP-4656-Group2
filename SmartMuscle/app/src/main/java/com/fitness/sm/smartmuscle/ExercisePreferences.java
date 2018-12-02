package com.fitness.sm.smartmuscle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.fitness.sm.smartmuscle.helpers.ExerciseDataOb;
import com.fitness.sm.smartmuscle.helpers.LinearLayoutWrapper;

import java.util.ArrayList;
import java.util.List;

public class ExercisePreferences extends Fragment {
    List<ExerciseDBObject> exercises;
    List<ParentObject> exDOs;
    RecyclerView settingRecycler;
    PreferenceAdapter adapter;


    public ExercisePreferences() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_exercise_preferences, container, false);

        if(exercises == null){
            init();
        }
        Log.d("EXERCISES_COMPILED",exercises.toString());

        settingRecycler = (RecyclerView)v.findViewById(R.id.recycler);
        settingRecycler.setLayoutManager(new LinearLayoutWrapper(getActivity()));
        adapter = new PreferenceAdapter(getActivity(),exDOs);
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);
        settingRecycler.setAdapter(adapter);
        //ScrollView sv = (ScrollView) v.findViewById(R.id.scrollview);
        //sv.addView(settingRecycler);
        /*updateUI();
        if (adapter == null) {
            //This would be a good place to initialize the daily workout
            adapter = new PreferenceAdapter(exercises);
            settingRecycler.setAdapter(adapter);
        }else{
            // if the workout needs to be updated this would be a good spot for it
            settingRecycler.setAdapter(adapter);
        }*/
        return v;
    }

    private List<ExerciseDBObject> init(){
        exDOs = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ExerciseDB exerciseDb = ExerciseDB.getInstance(getActivity());
                exercises = exerciseDb.dao().fetchAllExercises();
                if (exercises.size() == 0) {
                    return;
                }else{

                    for (int i = 0; i < exercises.size(); i++) {
                        ExerciseDBObject exercise = exercises.get(i);
                        exDOs.add(new ExerciseDataOb(exercises.get(i)));
                        Log.d("DataRetrieved", exercise.getName());
                    }
                }

            }});

        t.start();
        while (t.isAlive()){}

        return exercises;
    }

    private void updateUI() {
        if (adapter == null) {
            //This would be a good place to initialize the daily workout
            adapter = new PreferenceAdapter(getActivity(),exDOs);
            settingRecycler.setAdapter(adapter);
        }else{
            // if the workout needs to be updated this would be a good spot for it
            settingRecycler.setAdapter(adapter);
        }
    }








    private class PreferenceTitleHolder extends ParentViewHolder {
        private TextView tv;

        public PreferenceTitleHolder(/*LayoutInflater inflater, */@NonNull ViewGroup itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.exercise_title);
            tv.setText("Test");
        }

        public void bind(ExerciseDBObject e){
            if (tv != null){ tv.setText(e.getName());}
        }
    }

    private class PreferencesHolder extends ChildViewHolder implements View.OnClickListener {
        private EditText weight;
        private EditText sets;
        private EditText reps;
        private CheckBox wanted;
        private Button submit;
        private ExerciseDBObject ex;

        public PreferencesHolder(View itemView) {
            super(itemView);
            weight = (EditText) itemView.findViewById(R.id.weight_setting);
            sets = (EditText) itemView.findViewById(R.id.sets_setting);
            reps = (EditText) itemView.findViewById(R.id.reps_setting);
            wanted = (CheckBox) itemView.findViewById(R.id.isliked);
            submit = (Button) itemView.findViewById(R.id.submit);
            submit.setOnClickListener(this);
            wanted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        weight.setEnabled(true);
                        sets.setEnabled(true);
                        reps.setEnabled(true);
                        wanted.setChecked(true);
                    }else{
                        weight.setEnabled(false);
                        sets.setEnabled(false);
                        reps.setEnabled(false);
                        wanted.setChecked(false);
                    }
                }
            });

        }

        public void bind(ExerciseDBObject e){
            ex = e;
           weight.setText(Integer.toString(e.getWeight()));
           sets.setText(Integer.toString(e.getSets()));
           reps.setText(Integer.toString(e.getReps()));
           if(e.getWanted()==1){
               wanted.setChecked(true);
               weight.setEnabled(true);
               sets.setEnabled(true);
               reps.setEnabled(true);

           }else{
               wanted.setChecked(false);
               weight.setEnabled(false);
               sets.setEnabled(false);
               reps.setEnabled(false);
           }
        }


        @Override
        public void onClick(View v) {
            Log.d("UPDATE_DB","submit button clicked");
            if(ex!=null){
                Log.d("UPDATE_DB","submit button clicked");
                int w = Integer.parseInt(weight.getText().toString());
                if(w>0&&w<501){
                    ex.setWeight(w);
                }
                int s = Integer.parseInt(sets.getText().toString());
                if(s>0&&s<16){
                    ex.setSets(s);
                }
                int r = Integer.parseInt(reps.getText().toString());
                if(r>0&&r<101){
                    ex.setReps(r);
                }
                if(wanted.isChecked()){
                    ex.setWanted(1);
                }else{
                    ex.setWanted(0);
                }
                send(ex);
            }
        }
    }

    private void send(final ExerciseDBObject e) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ExerciseDB exerciseDb = ExerciseDB.getInstance(getActivity());
                exerciseDb.dao().updateExercise(e);
                Log.d("UPDATE_DB","DB updated");
            }
        }).start();
    }

    private class PreferenceAdapter extends ExpandableRecyclerAdapter<PreferenceTitleHolder,PreferencesHolder> {

        private List<ExerciseDBObject> ex;
        private List<ExerciseDataOb> pObs;
        private LayoutInflater layoutInflater;

        public PreferenceAdapter(Context context, List<ParentObject> parentItemList) {
            super(context, parentItemList);
            layoutInflater= LayoutInflater.from(context);
        }


        @NonNull
        @Override
        public PreferenceTitleHolder onCreateParentViewHolder(ViewGroup viewGroup) {
            return new PreferenceTitleHolder((ViewGroup)layoutInflater.inflate(R.layout.exercise_title_layout,viewGroup, false));
        }

        @Override
        public PreferencesHolder onCreateChildViewHolder(ViewGroup viewGroup) {
            return new PreferencesHolder((ViewGroup)layoutInflater.inflate(R.layout.exercise_settings,viewGroup, false));
        }

        @Override
        public void onBindParentViewHolder(PreferenceTitleHolder preferenceTitleHolder, int i, Object o) {
            ExerciseDataOb e = (ExerciseDataOb) o;
            preferenceTitleHolder.bind(e.getExercise());
        }

        @Override
        public void onBindChildViewHolder(PreferencesHolder preferencesHolder, int i, Object o) {
            ExerciseDBObject ob = (ExerciseDBObject)o;
            preferencesHolder.bind(ob);
            //preferencesHolder.bind();
        }

        @Override
        public int getItemCount() {
            return exercises.size();
        }
    }


}
