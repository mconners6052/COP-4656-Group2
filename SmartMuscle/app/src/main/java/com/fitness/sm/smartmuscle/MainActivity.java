package com.fitness.sm.smartmuscle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import static android.support.v7.widget.ListPopupWindow.MATCH_PARENT;
import static android.support.v7.widget.ListPopupWindow.WRAP_CONTENT;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private boolean sOpen = false;
    private Menu settings;
    private Fragment home;
    private WorkoutFragment workoutFrag;
    private FragmentManager fm;
    private Workout workout;// = Workout.get(getApplicationContext());
    private ExerciseDB exerciseDb;
    private ExerciseFragmentContainer efc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exerciseDb = ExerciseDB.getInstance(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        workout = Workout.get(getApplicationContext());
        home = new HomeFragment();
        workoutFrag = new WorkoutFragment();
        efc = new ExerciseFragmentContainer();
        efc.setWorkout(workout);
        workoutFrag.setEfc(efc);



        //setting fragment
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        settings = navigationView.getMenu();
        fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.main_frame);
        if (fragment == null) {
            fragment = home;
            fm.beginTransaction().add(R.id.main_frame, fragment).commit();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            if(sOpen){
                sOpen = settings(sOpen);
                checkUpdate();
            }else {
                drawer.closeDrawer(GravityCompat.START);
            }
        } else if(fm.getFragments().toString().contains("ExerciseFragmentContainer")) {
            fm.beginTransaction().replace(R.id.main_frame, workoutFrag).commit();
        } else if (fm.getFragments().toString().contains("WorkoutFragment")){
            fm.beginTransaction().replace(R.id.main_frame,home).commit();
        } else{
            Toast.makeText(this,"getFragments()"+fm.getFragments(),Toast.LENGTH_LONG).show();
            List<Fragment> fl = fm.getFragments();
            for (Fragment i:fl){
                Log.d("OPEN FRAGMENT: ",i.toString());
            }
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (id == R.id.nav_home) {
            Toast.makeText(this,"HOME",Toast.LENGTH_LONG).show();
            fm.beginTransaction().replace(R.id.main_frame,home).commit();
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_workout) {
            Toast.makeText(this,"WORKOUT",Toast.LENGTH_LONG).show();
            fm.beginTransaction().replace(R.id.main_frame, workoutFrag).commit();
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (id == R.id.nav_settings) {
            sOpen = settings(sOpen);
        }
        else if (id == R.id.settings_item1) {
            testDB();
        }
        else if (id == R.id.settings_item2) {
            workout.updateWorkout();
        }
        else if (id == R.id.settings_item3) {
            Toast.makeText(this,"Setting 3",Toast.LENGTH_LONG).show();
        }
        return true;
    }



    public void checkUpdate(){
        //every 24 hour update Workout
    }

    boolean settings(boolean state){ //will open and close settings menu
        ViewGroup.LayoutParams navparam = navigationView.getLayoutParams();
        if (state){
            navparam.width = WRAP_CONTENT;
            navigationView.setLayoutParams(navparam);
            navigationView.setCheckedItem(0);
            settings.getItem(3).setVisible(false);
            return false;
        }else{
            navparam.width = MATCH_PARENT;
            navigationView.setLayoutParams(navparam);
            settings.getItem(3).setVisible(true);
            return true;
        }
    }

    private void testDB(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ExerciseDBObject> exerciseList;
                exerciseList = exerciseDb.dao().fetchAllExercises();
                if (exerciseList.size() == 0) {
                    return;
                }else{

                    for (int i = 0; i < exerciseList.size(); i++) {
                        ExerciseDBObject exercise = exerciseList.get(i);
                        Log.d("DataRetrieved", exercise.getName());
                    }
                }

            }}).start();
    }
}
