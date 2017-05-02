package com.github.nkzawa.socketio.androidchat;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Drew on 4/30/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by andrey on 7/13/16.
 */
public class MessageDB extends SQLiteOpenHelper {



    private static final String DATABASE_NAME = "messages.db";
    private static final int DATABASE_VERSION = 1;



    // creates a table for the local database that will save steps
    //TODO: look inside DBConstants and create a string that will create a table with the correct datatypes.
    private static final String SQL_CREATE_ENTRIES = "create table "
            + DBConstants.TABLE_NAME  + " ("
            + DBConstants.MESSAGE_NUMBER + " Integer primary key, "
            + DBConstants.USERNAME + " Text, "
            + DBConstants.MESSAGE_TEXT + " Text );";






    // This constructor requires Context
    public MessageDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // this method will execute SQL_CREATE_ENTRIES to create the table with all the rows
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// we don't wanna do upgrades for this simple db
    }
}
