package com.codinginterview.wakeelahifield.codinginterviewpracticeapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class HashTableQuestions extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_table_questions);

        try {
            testJson();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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


    public void testJson() throws JSONException {
        TextView testView = (TextView) findViewById(R.id.Question);
        String json = loadJSONFromAsset();
        //testView.setText(json);

        //TODO: is there a better way to do this programmatically?

        RadioButton a1 = (RadioButton) findViewById(R.id.a1);
        RadioButton a2 = (RadioButton) findViewById(R.id.a2);
        RadioButton a3 = (RadioButton) findViewById(R.id.a3);
        RadioButton a4 = (RadioButton) findViewById(R.id.a4);
        RadioButton a5 = (RadioButton) findViewById(R.id.a5);

        JSONObject obj = new JSONObject(json);



        JSONArray hash_table_Qs = obj.getJSONArray("Hash Tables");
        String firstQuestion = hash_table_Qs.getJSONObject(0).getString("question");
        testView.setText(firstQuestion);

        JSONArray q1_answers = hash_table_Qs.getJSONObject(0).getJSONArray("choices");


        //TODO: again, can I do this automatically?
        a1.setText(q1_answers.getString(0));
        a2.setText(q1_answers.getString(1));
        a3.setText(q1_answers.getString(2));
        a4.setText(q1_answers.getString(3));
        a5.setText(q1_answers.getString(4));

    }

    public String loadJSONFromAsset() {
        String json;
        try {

            InputStream is = getAssets().open("questions.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

}
