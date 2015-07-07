package com.codinginterview.wakeelahifield.codinginterviewpracticeapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Devin on 6/23/2015.
 */
    public class SQLiteDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "QuizDatabase.db";
    public static final int DATABASE_VERSION = 1;
    private TopicDBManager topicTable;
    private QuestionDBManager questionTable;

    //basic table information I need
    protected static final String TOPIC_TABLE_NAME = "TopicTable";
    protected static final String TOPIC_COL = "topic";
    protected static final String ID_COL = "_id";

    //the SQL code for creating this table:
    private static final String CREATE_TOPIC_TABLE = "CREATE TABLE IF NOT EXISTS " + TOPIC_TABLE_NAME + "( " +
            ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TOPIC_COL + " TEXT" +
            ");";



    //QUESTION TABLE STRINGS & DEFINITIONS
    protected static final String QUESTION_TABLE_NAME = "QuestionsTable";
   // private static final String ID_COL = "_id"; I can reuse the old one because THEY'RE LITERALLY THE SAME and does that matter? I don't think so... correct me though, nullPointerExceptionError!
   protected static final String QUESTION_COL = "question";
    protected static final String CHOICES_COL = "choices";
    protected static final String ANSWER_COL = "answer";
    protected static final String TOPIC_ID = "topic_id";



    private static final String CREATE_QUESTION_TABLE = "CREATE TABLE IF NOT EXISTS " + QUESTION_TABLE_NAME + "(" +
            ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QUESTION_COL + " TEXT, " +
            CHOICES_COL + " TEXT, " +
            ANSWER_COL + " TINYINT, " +
            TOPIC_ID + " INTEGER, " +
            "FOREIGN KEY(" +TOPIC_ID + ") REFERENCES " + TOPIC_TABLE_NAME + "(" + ID_COL + ")" +
            ")  ;";




    public SQLiteDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){

        //topic table has to be created first because the question table depends upon it. the question table has a foreign key from the table topic.
        database.execSQL(CREATE_TOPIC_TABLE);
        database.execSQL(CREATE_QUESTION_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(SQLiteDBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data.");

        //there must be a way to do this in one line, but this was it's also better because I might get rid of a table later on then I just have to delete a line. So. who knows.
        db.execSQL("DROP TABLE IF EXISTS " + topicTable.getTableName());
        db.execSQL("DROP TABLE IF EXISTS " + questionTable.getTableName());
    }
}
