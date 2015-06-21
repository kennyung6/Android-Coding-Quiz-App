package com.codinginterview.wakeelahifield.codinginterviewpracticeapp.database;

import android.provider.BaseColumns;

/**
 * Created by zuhayrelahi1 on 6/21/15.
 */
public final class QuestionsTableContract {

    /* Constructor to prevent someone from instantiating the class
     * giving it an empty constructor
     */
    public QuestionsTableContract(){

    }

    /* Inner class that defines the table contents */
    public static abstract class AlgorithmsTable implements BaseColumns {
        public static final String TABLE_NAME = "Algorithms";
        public static final String COLUMN_NAME_ENTRY_ID = "question_id";
        public static final String COLUMN_NAME_OptA = "optA";
        public static final String COLUMN_NAME_OptB = "optB";
        public static final String COLUMN_NAME_OptC = "optC";
        public static final String COLUMN_NAME_ANSWER = "answer";
    }
}
