package com.codinginterview.wakeelahifield.codinginterviewpracticeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devin on 6/24/2015.
 *
 * Basically this is a helper class to manage the com.codinginterview.wakeelahifield.codinginterviewpracticeapp.Topic table
 * This is a separate class because I didn't want to have com.codinginterview.wakeelahifield.codinginterviewpracticeapp.SQLiteDBHelper to get bloated and explode
 */
public class TopicDBManager {

    private SQLiteDatabase db;
    private SQLiteDBHelper dbHelper;

    //basic table information I need
    private static final String TABLE_NAME = "TopicTable";
    private static final String TOPIC_COL = "topic";
    private static final String ID_COL = "_id";

    //the SQL code for creating this table:
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +
            ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT " +
            TOPIC_COL + " TEXT" +
            ");";


    public TopicDBManager(Context context){
        dbHelper = new SQLiteDBHelper(context);
    }

    public void open() throws SQLException{
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public String getCreateSQL(){
        return CREATE_TABLE;
    }

    public String getTableName(){
        return TABLE_NAME;
    }

    public void insertTopic(String topicName){
        ContentValues values = new ContentValues();
        values.put(TOPIC_COL,topicName);

        db.insert(TABLE_NAME, null, values);
    }

    public int getTopicID(String topic){

        Cursor cursor = db.query(TABLE_NAME, new String[] {ID_COL}, "TOPIC = " + topic, null, null, null, null);

        cursor.close();

        // Ummmmm? I don't know how columns are enumerated. does it start at 0? google tell me! nvm I don't need it
        return cursor.getInt(cursor.getColumnIndex(ID_COL));
    }

    public List<String> getAllTopicNames(){
        List<String> topics = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, new String[] {TOPIC_COL}, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            topics.add(cursor.getString(cursor.getColumnIndex(TOPIC_COL)));
        }
        cursor.close();

        return topics;
    }


}
