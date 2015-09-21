package com.codinginterview.wakeelahifield.codinginterviewpracticeapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Devin on 6/23/2015.
 *
 * The Helper Database Class. Considering consolidating all the database operations into this class, although it would be nice to be have separation for better organization.
 */
    public class SQLiteDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "QuizDatabase.db";
    public static final int DATABASE_VERSION = 4;

    //basic table information I need
    protected static final String TOPIC_TABLE_NAME = "TopicTable";
    protected static final String TOPIC_COL = "topic";
    protected static final String ID_COL = "_id";

    //the SQL code for creating this table:
    private static final String CREATE_TOPIC_TABLE = "CREATE TABLE IF NOT EXISTS " + TOPIC_TABLE_NAME + "( " +
            ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TOPIC_COL + " TEXT NOT NULL UNIQUE" +
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
            QUESTION_COL + " TEXT NOT NULL UNIQUE, " +
            CHOICES_COL + " TEXT NOT NULL, " +
            ANSWER_COL + " TINYINT NOT NULL, " +
            TOPIC_ID + " INTEGER, " +
            "FOREIGN KEY(" +TOPIC_ID + ") REFERENCES " + TOPIC_TABLE_NAME + "(" + ID_COL + ")" +
            ")  ;";


    //HISTORY TABLE STRINGS & DEFINITIONS
    //this table is to track how well the user has been over the past whatever
    protected static final String HISTORY_TABLE_NAME = "HistoryTable";
    protected static final String QUESTION_ID_COL = "q_id";
    protected static final String USER_ANSW_COL = "user_answer";
    protected static final String DATE_COL = "date";
    protected static final String GOT_RIGHT_COL = "got_right";

    private static final String CREATE_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS " + HISTORY_TABLE_NAME + "(" +
            ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_ANSW_COL + " INTEGER NOT NULL, " +
            GOT_RIGHT_COL + " INTEGER DEFAULT 0, " +
            DATE_COL + " INTEGER DEFAULT CURRENT_TIMESTAMP, " +
            QUESTION_ID_COL + " INTEGER, " +
            "FOREIGN KEY(" + QUESTION_ID_COL + ") REFERENCES " + QUESTION_TABLE_NAME + "(" + ID_COL + ")" +
            ");";


    public SQLiteDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){

        //topic table has to be created first because the question table depends upon it. the question table has a foreign key from the table topic.
        database.execSQL(CREATE_TOPIC_TABLE);
        database.execSQL(CREATE_QUESTION_TABLE);
        database.execSQL(CREATE_HISTORY_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(SQLiteDBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data.");

        //there must be a way to do this in one line, but this was it's also better because I might get rid of a table later on then I just have to delete a line. So. who knows.
        db.execSQL("DROP TABLE IF EXISTS " + TOPIC_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QUESTION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + HISTORY_TABLE_NAME);

        db.execSQL(CREATE_TOPIC_TABLE);
        db.execSQL(CREATE_QUESTION_TABLE);
        db.execSQL(CREATE_HISTORY_TABLE);
    }
}