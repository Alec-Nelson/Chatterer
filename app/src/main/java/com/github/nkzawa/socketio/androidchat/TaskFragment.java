package com.github.nkzawa.socketio.androidchat;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;



/**
 * Created by Andrey on 2/16/2017.
 */

public class TaskFragment extends Fragment implements RetainedFragmentInteraction {

    ActivityInteraction activity;
    String name;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ActivityInteraction)context;
        checkIfLoggedIn();
    }

    public static final String TAG_TASK_FRAGMENT = "task_fragment";
    private String mActiveFragmentTag;

    private IsUserLoggedInAsyncTask logincheck;
    //private ActivityInteraction activity;

    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }
    public TaskFragment() {
        // Required empty public constructor
    }

    public void setName(String st)
    {
        name = st;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public String getActiveFragmentTag() {
        return mActiveFragmentTag;
    }

    public void setActiveFragmentTag(String s) {
        mActiveFragmentTag = s;
    }

    @Override
    public void checkIfLoggedIn() {

        logincheck = new IsUserLoggedInAsyncTask(getActivity(),this);
        logincheck.execute();

    }

    @Override
    public void loginResult(String result) {
        setName(result);
    }

    @Override
    public void startBackgroundServiceNeeded() {
        IsUserLoggedInAsyncTask userLoggedInAsyncTask = new IsUserLoggedInAsyncTask(this.getActivity().
                getApplication().getApplicationContext(), this);
        userLoggedInAsyncTask.execute(name);
    }


}
