package com.codinginterview.wakeelahifield.codinginterviewpracticeapp;

import java.sql.Timestamp;

/**
 * Created by Devin on 8/30/2015.
 *
 * This class will organize the history of a question and do some basic analysis on it.
 *
 * Things in the History DB table atm:
 * "CREATE TABLE IF NOT EXISTS " + HISTORY_TABLE_NAME + "(" +
 ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
 USER_ANSW_COL + " INTEGER NOT NULL, " +
 GOT_RIGHT_COL + " INTEGER DEFAULT 0, " +
 "FOREIGN KEY(" + QUESTION_ID_COL + ") REFERENCES " + QUESTION_TABLE_NAME + "(" + ID_COL + ")" +
 ");";
 */
public class History {
    private Timestamp date;
    private int userAnswer;
    private boolean gotRight;

    public History(Timestamp date, boolean gotRight, int userAnswer){
        this.date = date;
        this.gotRight = gotRight;
        this.userAnswer = userAnswer;
    }

    public int getUserAnswer(){
        return userAnswer;
    }

    public Timestamp getDate(){
        return date;
    }

    public boolean isRight(){
        return gotRight;
    }
}

