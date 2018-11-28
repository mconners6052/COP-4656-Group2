package com.fitness.sm.smartmuscle;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;

import com.fitness.sm.smartmuscle.helpers.DaoAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {ExerciseDBObject.class}, version = 1,exportSchema = false)
public abstract class ExerciseDB extends RoomDatabase{

    public abstract DaoAccess dao();

    private static ExerciseDB INSTANCE;

    public synchronized static ExerciseDB getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = buildDb(context);
        }
        return INSTANCE;
    }

    private static ExerciseDB buildDb(final Context context){ //this method will construct the DB from JSON data
        return Room.databaseBuilder(context,ExerciseDB.class,"exercise_db")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) { //This will happen during database onCreate()
                        super.onCreate(db);
                        Log.d("DB_BUILD","DB onCreate() callback");
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                List<ExerciseDBObject> ex = new ArrayList<>();
                                //ex.add(new ExerciseDBObject("TestURL","TestName",1));
                                try{
                                    Resources r = context.getResources();
                                    InputStream is = r.openRawResource(R.raw.back); //read in JSON data from input stream
                                    int size = is.available();
                                    byte[] buffer = new byte[size];
                                    is.read(buffer);
                                    is.close();
                                    JSONArray jsData = new JSONArray(new String(buffer, "UTF-8")); //parse JSON
                                    for (int i=0;i<jsData.length();i++) { //this will convert the JSON Array into ExerciseDBObjects for the database
                                        JSONObject ob = jsData.getJSONObject(i);
                                        ex.add(new ExerciseDBObject(ob.getString("url"), ob.getString("name"), ob.getString("steps"), ob.getInt("mg"), ob.getInt("set"), ob.getInt("rep"), ob.getInt("weight"), ob.getInt("wanted")));
                                        Log.d("DB_BUILD",ob.getString("name")+" added to DB");
                                    }
                                } catch (JSONException e){
                                    e.printStackTrace();
                                    Log.e("DB_DATA_COMPILER","Failed to compile data for database due to JSON Exeption");
                                } catch (IOException er) {
                                    er.printStackTrace();
                                    Log.e("DB_DATA_COMPILER","Failed to compile data for database due to IO Exception");
                                } finally {
                                    getInstance(context).dao().insertExercises(ex); //add exercises to database
                                }
                            }
                        });

                    }
                }).build();
    }
}
