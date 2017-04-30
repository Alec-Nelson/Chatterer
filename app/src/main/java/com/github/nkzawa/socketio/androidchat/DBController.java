package com.github.nkzawa.socketio.androidchat;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import io.socket.client.Socket;

/**
 * Created by Drew on 4/30/2017.
 */

public class DBController {
    private SQLiteDatabase db;
    private MessageDB db_helper;
    private volatile Boolean processing = false;
    private Context context;
    private Socket mSocket;
    private int id;

    public  DBController(Context context, Application application) {
        db_helper = new MessageDB(context);
        this.context = context;
        id = 0;

        mSocket.connect();
        OpenDB();
    }



    // You need to call this open up the database
    public void OpenDB() {
        db = db_helper.getWritableDatabase();
    }


    // When the app is no longer using the database you must release the resources. This method will do that for you.
    public void CloseDB() {
        db.close();
        db = null;
    }


    // To avoid any complications that could happen due to simultaneous write/read operations you must make sure to check if the DB is not busy.
    private boolean IsFree() {
        return !processing;
    }


    // After the steps have been uploaded to the website the phone receives confirmation in the form
    // of timestamps for each of the step entries. Since we know that the confirmed steps are on the server we no longer need them in the
    // local database. In this method you need to remove all the rows that have been confirmed.
    public void RemoveMessages() {
        Log.d("db","remove old messages isFree "+IsFree());

        //TODO: here you perform the operation that will delete the confirmed steps as indicated by the 'epoch' timestamps inside 'confirmed'
        db.delete(DBConstants.TABLE_NAME, DBConstants.MESSAGE_NUMBER + " < " + (id -200) + " )", null);

    }

    public void InsertMessage(Message message) {

        Log.d("db","insert message called id db open ?"+db.isOpen()+" IsFree to write? "+IsFree());

        ContentValues values = new ContentValues();
        values.put(DBConstants.MESSAGE_NUMBER, id + "");
        values.put(DBConstants.USERNAME, message.getUsername());
        values.put(DBConstants.MESSAGE_TEXT, message.getMessage());

        db.insert(DBConstants.TABLE_NAME, null, values);
        id++;

    }

}
