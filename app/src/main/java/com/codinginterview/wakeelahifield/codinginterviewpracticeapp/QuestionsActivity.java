package com.codinginterview.wakeelahifield.codinginterviewpracticeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        writeQuestion();
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

    //TODO: make it so that it can switch from one question to the next after being answered.
    //TODO: keep track of which questions have been answered that works after app has been closed and turned off (write to file!)
    //TODO: come up with a way to find which question to ask next. random? sequential?

    public void writeQuestion() {
        try {
            testJson();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void testJson() throws JSONException {
        // get question textview thing so I can put in the question
        TextView TopicText = (TextView) findViewById(R.id.title);
        Intent intent = getIntent();
        String topicName = intent.getStringExtra("topic_name");

        TopicText.setText(topicName);

        TextView questionText = (TextView) findViewById(R.id.Question);

        DBManager db = new DBManager(this);
        try{
            db.open();

            RadioButton a1 = (RadioButton) findViewById(R.id.a1);
            RadioButton a2 = (RadioButton) findViewById(R.id.a2);
            RadioButton a3 = (RadioButton) findViewById(R.id.a3);
            RadioButton a4 = (RadioButton) findViewById(R.id.a4);
            RadioButton a5 = (RadioButton) findViewById(R.id.a5);

            List<Question> questions = db.getQuestionOfTopic(topicName);

            Topic theTopic = new Topic(topicName, questions);

            Question aQuestion = theTopic.getRandomQuestion();

            questionText.setText(aQuestion.get_question());

            String[] choices = new String[aQuestion.getChoices().size()];
            choices = aQuestion.getChoices().toArray(choices);

            a1.setText(choices[0]);
            a2.setText(choices[1]);
            a3.setText(choices[2]);
            a4.setText(choices[3]);
            a5.setText(choices[4]);



            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        /*
        String json = loadJSONFromAsset();



        //TODO: is there a better way to do this programmatically?
        //Get all of the RadioButtons
        RadioButton a1 = (RadioButton) findViewById(R.id.a1);
        RadioButton a2 = (RadioButton) findViewById(R.id.a2);
        RadioButton a3 = (RadioButton) findViewById(R.id.a3);
        RadioButton a4 = (RadioButton) findViewById(R.id.a4);
        RadioButton a5 = (RadioButton) findViewById(R.id.a5);


        JSONObject obj = new JSONObject(json);



        //get the question
        JSONArray hash_table_Qs = obj.getJSONArray("Hash Tables");
        String firstQuestion = hash_table_Qs.getJSONObject(0).getString("question");
        testView.setText(firstQuestion);

        //get the choices to answering the question into JSONArray
        JSONArray q1_answers = hash_table_Qs.getJSONObject(0).getJSONArray("choices");


        //TODO: again, can I do this automatically?
        //fill radio buttons with the choices
        a1.setText(q1_answers.getString(0));
        a2.setText(q1_answers.getString(1));
        a3.setText(q1_answers.getString(2));
        a4.setText(q1_answers.getString(3));
        a5.setText(q1_answers.getString(4));
        */

    }

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

}
