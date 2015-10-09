package com.codinginterview.wakeelahifield.codinginterviewpracticeapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class TopicActivity extends ListActivity {

    DBManager topicDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);



        //the database! it works! yay!
        topicDB = new DBManager(this);

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
        Intent intent = new Intent(this, QuestionsActivity.class);
        startActivity(intent);
    }

    public void write_topics() throws JSONException{
        try{
            String json = loadJSONFromAsset();
            //JSONObject questions = new JSONObject(json);
            JSONArray topics = new JSONArray(json);

            List<Topic> topicList = new ArrayList<>();

            for(int i = 0; i < topics.length(); i++){
                //TODO: discuss if it is inneficient to make objects out of everything, then store them in the database... will I use the objects I make here? This seems like a waste...
                Topic newTopic = new Topic(topics.getJSONObject(i));
                topicList.add(newTopic);
                addTopicToDatabase(newTopic);
            }

            ArrayAdapter<Topic> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, topicList);
            setListAdapter(adapter);


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //do something here using the position in the array
        //Log.d("############", "clicked on position " + position + " which has this string: " + getListAdapter().getItem(position).toString());
        Intent intent = new Intent(this, QuestionsActivity.class);
        intent.putExtra("topic_name", getListAdapter().getItem(position).toString());
        startActivity(intent);
    }



    public String loadJSONFromAsset() throws IOException{
        String json;
        try {

            InputStream is = getAssets().open("questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            int readSize = is.read(buffer);

//            if(readSize <0 ){
//                //end of input stream
//            }
            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


    public void addTopicToDatabase(Topic topic){
        DBManager topicDB = new DBManager(this);

        try{
            //open database and make it possible to be used.
            // Also note these 2 lines are why we are in a try/catch
            topicDB.open();
         //   quesDB.open();

            //obviously, insert topic into DB
            topicDB.insertTopic(topic.toString());

            List<Question> questions = topic.questions;
            for (int i = 0; i < questions.size(); i++){
                topicDB.insertQuestion(questions.get(i), topic.toString());
            }

            topicDB.close();
         //   quesDB.close();
        }catch (Exception e){
            e.printStackTrace();
        }
//        Button button = (Button) findViewById(R.id.hash_table);
//
//        DBManager topicDB = new DBManager(this);
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
