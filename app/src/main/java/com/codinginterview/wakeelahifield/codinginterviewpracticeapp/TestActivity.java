package com.codinginterview.wakeelahifield.codinginterviewpracticeapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    TextView helloWorld;
    TextView testText;
    DBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initialize();

        startTest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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

    public void initialize(){
        helloWorld = (TextView) findViewById(R.id.hello_world);
        testText = (TextView) findViewById(R.id.testText);

        db = new DBManager(this);
    }

    public void startTest(){
        try{
            db.open();

            Question testQuestion = db.getQuestionsFromTopic("Hash Tables").get(0);
            db.addToHistory(testQuestion, 0);

            db.getQuestionHistory(testQuestion);

            db.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
