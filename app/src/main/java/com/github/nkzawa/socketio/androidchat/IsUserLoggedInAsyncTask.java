package com.github.nkzawa.socketio.androidchat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Andrey on 3/1/2017.
 */

public class IsUserLoggedInAsyncTask extends AsyncTask<String,Void,String> {

    private RetainedFragmentInteraction taskFragment;
    SharedPreferences prefs;

    private Context context;
    public IsUserLoggedInAsyncTask(Context context, TaskFragment mTaskFragment){

        this.context=context;
        this.taskFragment =(RetainedFragmentInteraction)mTaskFragment;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    protected String doInBackground(String... strings) {
        String  mUsername = prefs.getString("username", "");
        if (mUsername != null){
            SharedPreferences.Editor e = prefs.edit();
            //e.putString("username", strings[0]);
            //e.commit();
        }
        mUsername = prefs.getString("username", "");
        return mUsername;
    }

    @Override
    protected void onPostExecute(String status) {
        taskFragment.loginResult(status);
    }

    private String isUserLoggedIn() throws IOException, JSONException {
        return prefs.getString("username", "");
    }

}