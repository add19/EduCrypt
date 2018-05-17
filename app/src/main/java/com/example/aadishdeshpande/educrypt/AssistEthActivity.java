package com.example.aadishdeshpande.educrypt;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

public class AssistEthActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;
    LineGraphSeries<DataPoint> series1;
    LineGraphSeries<DataPoint> series2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assist_eth);

        fetchDataEth process = new fetchDataEth();
        process.execute();
        double y,x,z,a;
        x = 0;


        GraphView graph = (GraphView) findViewById(R.id.graph1);
        series = new LineGraphSeries<DataPoint>();
        series1 = new LineGraphSeries<DataPoint>();
        series2 = new LineGraphSeries<DataPoint>();
        a = fetchDataEth.avg;
        // series.setDrawBackground(true);
        double diff = 0.0;
        graph.setBackgroundColor(Color.argb(100, 0, 0, 250));
        for(int i =0; i<30; i++) {
            x = i;
            y = fetchDataEth.high[i];
            z = fetchDataEth.low[i];

            if(y < a && i >= 15)
                diff++;

            series.appendData(new DataPoint(x, y), true, 100);
            series1.appendData(new DataPoint(x, z), true, 100);
            series2.appendData(new DataPoint(x, a),true,100);
        }
        series.setColor(Color.GREEN);
        series1.setColor(Color.RED);
        series.setTitle("Highest Values");
        series1.setTitle("Lowest Values");
        series2.setTitle("Average Value");
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series1.setDrawDataPoints(true);
        series1.setDataPointsRadius(10);

        graph.addSeries(series);
        graph.addSeries(series1);
        graph.addSeries(series2);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(30);
        graph.getViewport().setMinY(0.0);
        graph.getViewport().setMaxY(2000.0);

        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(AssistEthActivity.this, "The highest value of ETH on the day: "+ (int)dataPoint.getX() + " April is " + dataPoint.getY(), Toast.LENGTH_SHORT).show();
            }
        });


        series1.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(AssistEthActivity.this, "The lowest value of ETH on the day: "+ (int)dataPoint.getX() + " April is " + dataPoint.getY(), Toast.LENGTH_SHORT).show();
            }
        });


        series2.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(AssistEthActivity.this, "The Average value of ETH for the past 30 days: "+ (int)dataPoint.getX() + " April is " + dataPoint.getY(), Toast.LENGTH_SHORT).show();
            }
        });
        /*graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);*/

        if(diff < 7){
            Toast.makeText(AssistEthActivity.this, "There is a strong possibility of profit by selling few!", Toast.LENGTH_LONG).show();
        }
        if(diff > 7){
            Toast.makeText(AssistEthActivity.this, "The value has fallen too many times in the past 15 days, Not recommended to sell!", Toast.LENGTH_LONG).show();
        }
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
    }
}
