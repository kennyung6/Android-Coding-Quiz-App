package com.codinginterview.wakeelahifield.codinginterviewpracticeapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class TopicActivity extends ListActivity {

    TopicDBManager topicDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);



        //the database! it works! yay!
        topicDB = new TopicDBManager(this);

        //now I have to open it :P
        try{
            topicDB.open();
        }catch (Exception e){
            e.printStackTrace();
        }

//        setButton();

        //load topics from json and put it in database and UI
        try{
            write_topics();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topics, menu);
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

    public void to_hashTables(View view){
        Intent intent = new Intent(this, HashTableQuestions.class);
        startActivity(intent);
    }

    public void write_topics() throws JSONException{
        try{
            String json = loadJSONFromAsset();
            //JSONObject questions = new JSONObject(json);
            JSONArray topics = new JSONArray(json);

            List<Topic> topicList = new ArrayList<Topic>();
            for(int i = 0; i < topics.length(); i++){

            }


        }catch (Exception e){
            e.printStackTrace();
        }



    }

    public String loadJSONFromAsset() throws IOException{
        String json;
        try {

            InputStream is = getAssets().open("questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            int rsz = is.read(buffer);

            if(rsz <0 ){
                //there is a big problem and I don't know what to do
                IOException e = new IOException("Could not read from buffer");
                throw e;
            }
            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


    public void setButton(){
//        Button button = (Button) findViewById(R.id.hash_table);
//
//        TopicDBManager topicDB = new TopicDBManager(this);
//        try{
//            topicDB.open();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        topicDB.insertTopic("bloop");
//        int topID = topicDB.getTopicID("bloop");
//
//        button.setText("blah " + topID);
//
//



//        button.setText("bloop");
    }
}
