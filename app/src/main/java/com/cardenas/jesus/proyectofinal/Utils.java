package com.cardenas.jesus.proyectofinal;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static void llenar(BarChart chart, List<String> dataModel, List<Double> datos) {

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        String[] labels = new String[dataModel.size()];

        dataModel.toArray(labels);
        for (int i = 0; i < datos.size(); i++) {
            ArrayList<BarEntry> values = new ArrayList<>();
            values.add(new BarEntry(i, datos.get(i).floatValue()));

            BarDataSet set1;

            set1 = new BarDataSet(values, labels[i]);
            set1.setColor(darColor(labels[i]));
            set1.setDrawValues(false);


            dataSets.add(set1);
        }
        BarData data = new BarData(dataSets);
        chart.setData(data);
        chart.setFitBars(true);

        chart.invalidate();
    }

    private static int darColor(String label) {
        switch (label){
            case "NO2": return Color.BLUE;
            case "PM10": return Color.GREEN;
            case "CO": return Color.RED;
            case "O3": return Color.YELLOW;
            default: return Color.DKGRAY;
        }
    }
}
