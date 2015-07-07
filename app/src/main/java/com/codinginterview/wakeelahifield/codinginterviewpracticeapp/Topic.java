package com.codinginterview.wakeelahifield.codinginterviewpracticeapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Devin on 6/17/2015.
 *
 * This class will just hold an array of questions for a topic and data for how well you're doing
 * for that topic.
 */
public class Topic {
    ArrayList<Question> questions;
    String topic;
    List<Question> shuffledQs;
    int qIndex;
    private final String NO_QUESTIONS_EXCEPTION = "There are no questions loaded. You have a problem.";


    /*
     * Constructor for a com.codinginterview.wakeelahifield.codinginterviewpracticeapp.Topic. A topic holds an array of questions.
     *
     * HAS NOT BEEN TESTED YET
     * TODO: TEST THIS SHIT
     */
    public Topic (JSONObject jsonTopic) {
        questions = new ArrayList<Question>();

        try {
            topic = jsonTopic.getString("topic");
            JSONArray jsonQuestions = jsonTopic.getJSONArray("questions");
            for (int i = 0; i < jsonQuestions.length(); i++) {
                //make new question object out of this json question object
                Question newQuestion = new Question(jsonQuestions.getJSONObject(i));
                //insert it like a baos
                questions.add(newQuestion);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        qIndex = 0;
        shuffleQuestions();
    }

    private void shuffleQuestions() throws IllegalStateException{
        if(questions.size() < 1){
            throw new IllegalStateException(NO_QUESTIONS_EXCEPTION);
        }
        shuffledQs = Arrays.asList((Question [] )questions.toArray());
        Collections.shuffle(shuffledQs);
    }

    public Question GetRandomQuestion() throws IllegalStateException{
        //for this, I'm not assuming questions is not empty. If it is empty, nothing will work but someone should find out somehow
        if(shuffledQs.size() <1){
            throw new IllegalStateException(NO_QUESTIONS_EXCEPTION);
        }
        Question newQuestion = shuffledQs.get(qIndex);
        qIndex++;
        return newQuestion;
    }
}
