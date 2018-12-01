package com.fitness.sm.smartmuscle;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    Calendar calendar = Calendar.getInstance();
    private int mCurrentIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
    private ImageView mWorkoutButton;
    private TextView mQuoteTextView;
    private View homeView;
    private WorkoutFragment wof;
    private Workout workout;
    private Quote[] mQuoteBank = new Quote[] {
            new Quote(R.string.quote00),
            new Quote(R.string.quote01),
            new Quote(R.string.quote02),
            new Quote(R.string.quote03),
            new Quote(R.string.quote04),
            new Quote(R.string.quote05),
            new Quote(R.string.quote06),
    };

    public HomeFragment(){}

    public HomeFragment(WorkoutFragment wof, Workout workout) {
        this.wof = wof;
        this.workout = workout;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        homeView = inflater.inflate(R.layout.fragment_home, container, false);

        //Changes Quote of the day
        mQuoteTextView = homeView.findViewById(R.id.quote_text_view);
        updateQuote();

        //Workout Button Clickable
        mWorkoutButton = homeView.findViewById(R.id.workout_button);
        SetImage(mWorkoutButton);
        mWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(homeView.getContext(),"WORKOUT",Toast.LENGTH_LONG).show();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.main_frame, wof).commit();
            }
        });

        return homeView;
    }

    private void SetImage(ImageView ib) {
        switch (workout.MuscleChoice()) //change these for images
        {
            case 0:
                ib.setImageResource(R.drawable.ic_arm_day);
                break;
            case 1:
                ib.setImageResource(R.drawable.ic_leg_day);
                break;
            case 2:
                ib.setImageResource(R.drawable.ic_chest_day);
                break;
            case 3:
                ib.setImageResource(R.drawable.ic_back_day);
                break;


        }
    }

    private void updateQuote()
    {
        int quote = mQuoteBank[mCurrentIndex].getmTextResID();
        mQuoteTextView.setText(quote);
    }

    private class Quote
    {
        int mTextResID;

        public Quote(int TextResID)
        {
            mTextResID = TextResID;
        }

        public int getmTextResID() {
            return mTextResID;
        }
    }

}
