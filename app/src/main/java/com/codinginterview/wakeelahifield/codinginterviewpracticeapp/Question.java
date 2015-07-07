package com.codinginterview.wakeelahifield.codinginterviewpracticeapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Devin on 6/16/2015.
 *
 * This class is intended to hold a _question, hold the possible choices for answering the _question,
 * and which choice is the correct answer.
 *
 * Intended to be used by HashTableQuestions, and, in future, whichever activity will be the
 * generalized activity where questions are asked of the user.
 */
public class Question {
    private String _question;
    private ArrayList<String> choices;
    private int answerIndex;

    /*
     * Constructor where the object is built with a JSONObject of a _question.
     *
     * HAS NOT BEEN TESTED YET
     * TODO: TEST THIS SHIT
     */
    public Question(JSONObject jsonQuestion){
        choices = new ArrayList<>();

        //try/catch because this is json
        try {
            _question = jsonQuestion.getString("question");
            answerIndex = jsonQuestion.getInt("answer");
            JSONArray jsonChoices = jsonQuestion.getJSONArray("choices");

            //populate choices
            for(int i = 0; i < jsonChoices.length(); i++){
                choices.add(i, jsonChoices.getString(i));
                //is there a difference between ^ and v?
                //choices.add(jsonChoices.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Question(String question, int answer, String[] newChoices){
        choices = new ArrayList<>(Arrays.asList(newChoices));
        answerIndex = answer;
        _question = question;
    }

    public String get_question(){
        return _question;
    }

    public int getAnswerIndex(){
        return answerIndex;
    }

    public ArrayList<String> getChoices(){
        return choices;
    }
}
