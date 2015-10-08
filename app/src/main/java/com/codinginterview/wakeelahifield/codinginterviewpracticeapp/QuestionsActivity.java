package com.codinginterview.wakeelahifield.codinginterviewpracticeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/*
 * Created by Devin Wakefield on June 7, 2015
 *
 * This class is a temporary activity to get a basic setup running. This will ask questions of the
 * user about hash tables, that has been stored as an asset as questions.json. Eventually, I hope
 * that I can make a generalized activity that can be told which topic has been chosen, so that
 * I only need one activity for all topics. The topic will be decided by the user, and this
 * generalized activity will get the questions about that topic based on the user input.
 *
 */

public class QuestionsActivity extends AppCompatActivity {

    RadioButton a1;
    RadioButton a2;
    RadioButton a3;
    RadioButton a4;
    RadioButton a5;

    RadioGroup choices;

    TextView questionText;
    List<Question> questions;
    Topic theTopic;
    Question currQuestion;

    DBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hash_table_questions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void initialize() {
        // get question textview thing so I can put in the question
        TextView TopicText = (TextView) findViewById(R.id.title);
        Intent intent = getIntent();
        String topicName = intent.getStringExtra("topic_name");
        choices = (RadioGroup) findViewById(R.id.answers);

        TopicText.setText(topicName);

        questionText = (TextView) findViewById(R.id.Question);

        db = new DBManager(this);
        try{
            db.open();

            a1 = (RadioButton) findViewById(R.id.a1);
            a2 = (RadioButton) findViewById(R.id.a2);
            a3 = (RadioButton) findViewById(R.id.a3);
            a4 = (RadioButton) findViewById(R.id.a4);
            a5 = (RadioButton) findViewById(R.id.a5);

            questions = db.getQuestionsFromTopic(topicName);

            theTopic = new Topic(topicName, questions);

            writeRandomQuestion();

            db.close();
        }catch (java.sql.SQLException e){
            e.printStackTrace();
        }
    }

    //TODO: keep track of which questions have been answered that works after app has been closed and turned off (write to file!)
    public void writeRandomQuestion(){

        currQuestion = theTopic.getRandomQuestion();

        questionText.setText(currQuestion.get_question());

        String[] choices = new String[currQuestion.getChoices().size()];
        choices = currQuestion.getChoices().toArray(choices);

        a1.setText(Html.fromHtml(choices[0]));
        a2.setText(Html.fromHtml(choices[1]));
        a3.setText(Html.fromHtml(choices[2]));
        a4.setText(Html.fromHtml(choices[3]));
        a5.setText(Html.fromHtml(choices[4]));

    }

    public void nextQuestion(View view){

//      you have to divide indexOfChild() by two because the lines in the UI are counted, and this way it just works out.
        int userChoice = choices.indexOfChild(choices.findViewById(choices.getCheckedRadioButtonId()))/2;
        boolean gotRight = currQuestion.getAnswerIndex() == userChoice;

        try{
            db.open();

            db.addToHistory(currQuestion, userChoice);
            Toast toast;
            if(gotRight){
                toast = Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT);
            }else {
                toast = Toast.makeText(getApplicationContext(), "Wrong :\'(", Toast.LENGTH_SHORT);
            }

            toast.show();

        } catch (Exception e){
            e.printStackTrace();
        }

        writeRandomQuestion();
    }

}
