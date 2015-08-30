package com.codinginterview.wakeelahifield.codinginterviewpracticeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        DBManager db = new DBManager(this);
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

        a1.setText(choices[0]);
        a2.setText(choices[1]);
        a3.setText(choices[2]);
        a4.setText(choices[3]);
        a5.setText(choices[4]);

    }

    public void nextQuestion(View view){


//        Toast toast;
        //you have to divide indexOfChild() by two because the lines in the UI are counted, and this way it just works out.
        if (currQuestion.getAnswerIndex() == (choices.indexOfChild(choices.findViewById(choices.getCheckedRadioButtonId())))/2 ) {
//            user got the question right! yay!
//            toast = Toast.makeText(getApplicationContext(), "Yay you did a thing, I guess? meh.", Toast.LENGTH_SHORT);

            //need to mark somewhere that the question was answered correctly
        } else {
//            user is WRONG WRONG WRONG OH MY GOODNESS SO SCARY <\3
//            toast = Toast.makeText(getApplicationContext(), "WRONG OMG YOU\'RE WRONG HOW COULD YOU WE ALL BELIEVED IN YOU BUT NOW WE\'RE ATHEISTS!!!", Toast.LENGTH_SHORT);
        }
//        toast = Toast.makeText(getApplicationContext(),
//                "you checked " + choices.indexOfChild(choices.findViewById(choices.getCheckedRadioButtonId()))/2 + " and the answer was " + currQuestion.getAnswerIndex(),
//                Toast.LENGTH_LONG);
//        toast.show();

        writeRandomQuestion();
    }

    /*

    Don't need anymore

    public String loadJSONFromAsset() {
        String json;
        try {

            InputStream is = getAssets().open("questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            int rsz = is.read(buffer);

            if(rsz <0 ){
                //there is a big problem and I don't know what to do
            }
            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
    */

}
