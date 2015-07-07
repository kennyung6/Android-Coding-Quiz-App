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



    public TopicDBManager(Context context){
        dbHelper = new SQLiteDBHelper(context);
    }

    public void open() throws SQLException{
        db = dbHelper.getWritableDatabase();
        int tmp = 1+1;
        tmp++;
        if(db == null)
        {
            SQLException e = new SQLException("database is null");
            throw e;
        }
    }

    public void close(){
        dbHelper.close();
    }

    public String getTableName(){
        return SQLiteDBHelper.TOPIC_TABLE_NAME;
    }

    public void insertTopic(String topicName){
        ContentValues values = new ContentValues();
        values.put(SQLiteDBHelper.TOPIC_COL,topicName);
        try {
            this.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.insert(SQLiteDBHelper.TOPIC_TABLE_NAME, null, values);
    }

    public int getTopicID(String topic){

        Cursor cursor = db.query(SQLiteDBHelper.TOPIC_TABLE_NAME, new String[] {SQLiteDBHelper.ID_COL}, SQLiteDBHelper.TOPIC_COL + " = \"" + topic + "\"", null, null, null, null);

        cursor.moveToFirst();
        int topicID = cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.ID_COL));
        cursor.close();

        // Ummmmm? I don't know how columns are enumerated. does it start at 0? google tell me! nvm I don't need it
        return topicID;
    }

    public List<String> getAllTopicNames(){
        List<String> topics = new ArrayList<>();

        Cursor cursor = db.query(SQLiteDBHelper.TOPIC_TABLE_NAME, new String[] {SQLiteDBHelper.TOPIC_COL}, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            topics.add(cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.TOPIC_COL)));
        }
        cursor.close();

        return topics;
    }


}
