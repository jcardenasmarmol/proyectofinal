package com.cardenas.jesus.proyectofinal.utilidades;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
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
            set1.setColor(darColor(labels[i], datos.get(i).floatValue()));
            set1.setDrawValues(false);


            dataSets.add(set1);
        }

        BarData data = new BarData(dataSets);

        chart.setData(data);

        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        ValueFormatter xAxisFormatter = new IndexAxisValueFormatter(labels);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setDrawLabels(true);


        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawLabels(true);
        leftAxis.setDrawGridLines(true);
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f);

        chart.setFitBars(true);

        chart.invalidate();
    }

    private static int darColor(String label, float valor) {
        switch (label){
            case "NO2": return colorNO2(valor);
            case "PM10": return colorPM10(valor);
            case "O3": return colorO3(valor);
            case "SO2": return colorSO2(valor);
            case "PM25": return colorPM25(valor);
            case "CO": return colorCO(valor);
            default: return Color.LTGRAY;
        }

    }

    private static int colorCO(float valor) {
        if (valor<10) return Color.GREEN;
        else return Color.RED;
    }

    private static int colorNO2(float valor){
        if (valor<30) return Color.GREEN;
        else if(valor<=40) return Color.YELLOW;
        else return Color.RED;
    }

    private static int colorPM10(float valor){
        if (valor<35) return Color.GREEN;
        else if(valor<=50) return Color.YELLOW;
        else return Color.RED;
    }

    private static int colorO3(float valor){
        if (valor<75) return Color.GREEN;
        else if(valor<=100) return Color.YELLOW;
        else return Color.RED;
    }

    private static int colorPM25(float valor){
        if (valor<20) return Color.GREEN;
        else if(valor<=25) return Color.YELLOW;
        else return Color.RED;
    }

    private static int colorSO2(float valor){
        if (valor<15) return Color.GREEN;
        else if(valor<=20) return Color.YELLOW;
        else return Color.RED;
    }
}
