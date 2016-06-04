package com.example.chiefcorlyns.scenes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class SaveRouteGraphView extends ActionBarActivity {

    EditText productBox;
    EditText quantityBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LineChartView lineChart = (LineChartView) findViewById(R.id.chart);


        List<PointValue>  entries = new ArrayList<>();
        entries.add(new PointValue(0, 4));
        entries.add(new PointValue(1, 8));
        entries.add(new PointValue(2, 6));
        entries.add(new PointValue(3, 2));
        entries.add(new PointValue(4, 8));
        entries.add(new PointValue(5, 4));
        entries.add(new PointValue(4, 4));



        Line line = new Line(ButtonFragment.getArray()).setColor(Color.BLUE).setCubic(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();

        data.setLines(lines);
        LineChartView chart = new LineChartView(this);

        lineChart.setLineChartData(data);

        lineChart.setHorizontalScrollBarEnabled(true);
        lineChart.animate();
        lineChart.offsetTopAndBottom(10);

    }
}
