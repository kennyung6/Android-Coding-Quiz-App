package com.codinginterview.wakeelahifield.codinginterviewpracticeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devin on 6/24/2015.
 *
 * Basically this is a helper class to manage the com.codinginterview.wakeelahifield.codinginterviewpracticeapp.Topic table
 * This is a separate class because I didn't want to have com.codinginterview.wakeelahifield.codinginterviewpracticeapp.SQLiteDBHelper to get bloated and explode
 */
public class DBManager {

    private SQLiteDatabase db;
    private SQLiteDBHelper dbHelper;



    public DBManager(Context context){
        dbHelper = new SQLiteDBHelper(context);
    }

    public void open() throws SQLException{
        db = dbHelper.getWritableDatabase();
        if(db == null)
        {
            throw new SQLException("database is null");
        }
    }

    public void close(){
        dbHelper.close();
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
            cursor.moveToNext();
        }
        cursor.close();

        return topics;
    }



    /*
     *
     * QUESTION DATABASE MANAGEMENT LAND! I merged these two DB manager things because it was becoming a mess
     *
     */

    protected static final String SEPARATOR = "_,_";


    private ContentValues getQuestionData(Question question){
        ContentValues values = new ContentValues();
        values.put(SQLiteDBHelper.QUESTION_COL, question.get_question());

        //serialize all the choices into one string so it can be stored in the database. Yay Java!
        String choicesSerial = TextUtils.join(SEPARATOR, question.getChoices().toArray());
        values.put(SQLiteDBHelper.CHOICES_COL, choicesSerial);

        values.put(SQLiteDBHelper.ANSWER_COL, question.getAnswerIndex());

        return values;
    }

    //if I happen to know the actual ID # (this might actually be better because I don't know if calling topicTable is actually a good idea from here? who knows.
    public void insertQuestion(Question newQuestion, int topicID){
        ContentValues values = getQuestionData(newQuestion);
        values.put(SQLiteDBHelper.TOPIC_ID, topicID);

        db.insert(SQLiteDBHelper.QUESTION_TABLE_NAME, null, values);
    }

    //if I only have the topic name in a string:q
    public void insertQuestion(Question newQuestion, String topicName){
//        ContentValues values = getQuestionData(newQuestion);
//        values.put(TOPIC_ID, topicTable.getTopicID(topicName));
//
//        db.insert(TABLE_NAME, null, values);

        insertQuestion(newQuestion, getTopicID(topicName));

    }

    private List<Question> getQuestionsFromCursor(Cursor cursor){
        List<Question> questions = new ArrayList<>();

        while(!cursor.isAfterLast()){
            questions.add(new Question(
                    cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.QUESTION_COL)),                           //gets question
                    cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.ANSWER_COL)),                                //gets answer index
                    TextUtils.split(cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.CHOICES_COL)), SEPARATOR) //gets answers as string array
            ));
            cursor.moveToNext();
        }

        return questions;
    }

    public List<Question> getAllQuestions(){
        //List<Question> questions = new ArrayList<>();

        Cursor cursor = db.query(
                SQLiteDBHelper.QUESTION_TABLE_NAME,
                new String[] {SQLiteDBHelper.QUESTION_COL, SQLiteDBHelper.CHOICES_COL, SQLiteDBHelper.ANSWER_COL},
                null, null, null, null, null);

        cursor.moveToFirst();

        List<Question> questions = getQuestionsFromCursor(cursor);

        cursor.close();

        return questions;
    }

    public List<Question> getQuestionsFromTopic(String topicName){

        int topicID = getTopicID(topicName);

        Cursor cursor = db.query(
                SQLiteDBHelper.QUESTION_TABLE_NAME,
                new String[] {SQLiteDBHelper.QUESTION_COL, SQLiteDBHelper.CHOICES_COL, SQLiteDBHelper.ANSWER_COL},
                SQLiteDBHelper.TOPIC_ID + " = \"" + topicID + "\"",
                null, null, null, null);

        cursor.moveToFirst();
        List<Question> questions = getQuestionsFromCursor(cursor);
        cursor.close();
        return questions;
    }

    public int getQuestionID(Question question){
        int id;

        Cursor cursor = db.query(
                SQLiteDBHelper.QUESTION_TABLE_NAME,
                new String[] {SQLiteDBHelper.ID_COL},
                SQLiteDBHelper.QUESTION_COL + " = \"" + question.get_question() + "\"",
                null, null, null, null);

        cursor.moveToFirst();
        id = cursor.getColumnIndex(SQLiteDBHelper.ID_COL);
        cursor.close();

        return id;
    }


    /*
     * HISTORY TABLE MANAGEMENT DEFINITIONS HERE
     * The history table is used to keep track of the user's history and all that stuff. woo
     */

    /*blah blah
            hahahha
    ahdkjflska
                    dklsjflksdjakldfa*/

    public void addToHistory(Question question, int userAnswer){
        int id = getQuestionID(question);

        ContentValues values = new ContentValues();
        values.put(SQLiteDBHelper.USER_ANSW_COL, userAnswer);
        values.put(SQLiteDBHelper.QUESTION_ID_COL, id);
        if(userAnswer == question.getAnswerIndex()){
            values.put(SQLiteDBHelper.GOT_RIGHT_COL, 1);
        } else {
            values.put(SQLiteDBHelper.GOT_RIGHT_COL, 0);
        }
        db.insert(SQLiteDBHelper.HISTORY_TABLE_NAME, null, values);
    }


    public void getQuestionHistory(Question question){
        int id = getQuestionID(question);

        Cursor cursor = db.query(
                SQLiteDBHelper.HISTORY_TABLE_NAME,
                new String[] {SQLiteDBHelper.USER_ANSW_COL, SQLiteDBHelper.GOT_RIGHT_COL},
                SQLiteDBHelper.ID_COL + " = \"" + id + "\"",
                null, null, null, null);

        //TODO: make a "history" object that can store all this in an arraylist or something
    }



}
