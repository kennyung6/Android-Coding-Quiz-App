package com.codinginterview.wakeelahifield.codinginterviewpracticeapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;

public class HistoryActivity extends AppCompatActivity {

    DBManager db;
    GraphView graph;
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
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

    public long daysFromNow(Timestamp time){
//        Calendar start = Calendar.getInstance();
//        start.setTimeInMillis(time.getTime());
//        Calendar today = Calendar.getInstance();
//
//        int diff = today.compareTo(start);
//        return diff;

        long milliseconds1 = time.getTime();
        long milliseconds2 = new Timestamp(Calendar.getInstance().getTimeInMillis()).getTime();

        long diff = milliseconds2 - milliseconds1;
//        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
//        long diffHours = diff / (60 * 60 * 1000);
//        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffMinutes;
    }

    public DataPoint[] convertHistoryArray(ArrayList<History> history){
        DataPoint[] points = new DataPoint[history.size()];
        int i = 0;
        for(History event : history){
            points[i] = new DataPoint(daysFromNow(event.getDate()), event.isRight() ? 1 : 0);
            i+=1;
        }

        Arrays.sort(points, new Comparator<DataPoint>() {
            @Override
            public int compare(DataPoint lhs, DataPoint rhs) {
                return Double.compare(lhs.getX(), rhs.getX());
            }
        });

        return points;
    }

    public void init(){

        db = new DBManager(getApplicationContext());
        ArrayList<ArrayList<History>> histories;
        graph = (GraphView) findViewById(R.id.graph);
        String testString = "Ghasp! ";
        test = (TextView) findViewById(R.id.hello);

        try{
            db.open();
            histories = db.getTopicHistory("Hash Tables");

            DataPoint[] dataPoints = convertHistoryArray(histories.get(0));
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
            graph.addSeries(series);
            for(DataPoint point : dataPoints){
                testString += "|| " + point.toString() + " ";
            }
            /*for(ArrayList<History> qHistory : histories){
                DataPoint[] dataPoints = convertHistoryArray(qHistory);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
                graph.addSeries(series);
                testString += "||" + qHistory.size() + ", " + dataPoints[0].toString() + " ";
            }*/

            test.setText(testString);

            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        //DataPoint[] questionHistory = new DataPoint[];

    }
}
