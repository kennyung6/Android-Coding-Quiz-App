package com.codinginterview.wakeelahifield.codinginterviewpracticeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.sql.SQLException;

/**
 * Created by Devin on 6/24/2015.
 *
 * Basically this is something to manage the database for the com.codinginterview.wakeelahifield.codinginterviewpracticeapp.Question table.
 * This is a separate class because I didn't want to have com.codinginterview.wakeelahifield.codinginterviewpracticeapp.SQLiteDBHelper to get bloated and explode
 */
public class QuestionDBManager {

    protected static final String SEPARATOR = "_,_";

    private SQLiteDatabase db;
    private static TopicDBManager topicTable;
    private SQLiteDBHelper dbHelper;


    public QuestionDBManager(Context context){
        dbHelper = new SQLiteDBHelper(context);
        topicTable = new TopicDBManager(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    //since I have two insert question things, most of which are the following function, why have same code in two spots when I can do this?
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

        insertQuestion(newQuestion, topicTable.getTopicID(topicName));

    }

   // public ArrayList<com.codinginterview.wakeelahifield.codinginterviewpracticeapp.Question> getAllQuestions(String topicName){
     //   return getAllQuestions(topicTable.getTopicID(topicName));
    //}

   // public ArrayList<com.codinginterview.wakeelahifield.codinginterviewpracticeapp.Question> getAllQuestions(int topicID){

    //}

}
