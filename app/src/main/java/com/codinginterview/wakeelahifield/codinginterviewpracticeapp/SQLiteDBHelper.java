package com.codinginterview.wakeelahifield.codinginterviewpracticeapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Devin on 6/23/2015.
 */
    public class SQLiteDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "QuizDatabase";
    public static final int DATABASE_VERSION = 1;
    private TopicDBManager topicTable;
    private QuestionDBManager questionTable;

    public SQLiteDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        //topic table has to be created first because the question table depends upon it. the question table has a foreign key from the table topic.
        database.execSQL(topicTable.getCreateSQL());
        database.execSQL(questionTable.getCreateSQL());
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(SQLiteDBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data.");

        //there must be a way to do this in one line, but this was it's also better because I might get rid of a table later on then I just have to delete a line. So. who knows.
        db.execSQL("DROP TABLE IF EXISTS " + topicTable.getTableName());
        db.execSQL("DROP TABLE IF EXISTS " + questionTable.getTableName());
    }
}
