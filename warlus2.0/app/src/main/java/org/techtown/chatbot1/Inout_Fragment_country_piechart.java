package org.techtown.chatbot1;

import android.app.AlertDialog;
import android.graphics.Color;
import android.icu.text.Edits;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Inout_Fragment_country_piechart extends Fragment {

    PieChart pieChart_country;
    HashMap<String,Integer> ex_datalist = new HashMap<>();
    Map<String,Integer> datalist;
    ArrayList<Integer> colors = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_inout_country_piechart, container, false);
        Bundle country_bundle = getArguments();
        Parceldata country_data = country_bundle.getParcelable("country_data");
        datalist = country_data.datalist;

        pieChart_country = (PieChart)rootView.findViewById(R.id.fragment_inout_country_piechart);
        graphInitSetting();
        showPieChart();

        pieChart_country.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                try {
                    int index = pieChart_country.getData().getDataSetForEntry(e).getEntryIndex((PieEntry)e);
                    Object[] data_key = ex_datalist.keySet().toArray();
                    Object[] data_value = ex_datalist.values().toArray();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("??????");
                    builder.setMessage(data_key[index].toString()+"\n"+"?????? ??????: "+data_value[index]);
                    builder.setCancelable(true);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }catch (Exception error){
                    error.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected() {
            }
        });

        return rootView;
    }

    public void graphInitSetting(){



        /*colors.add(Color.parseColor("#476567"));
        colors.add(Color.parseColor("#890567"));
        colors.add(Color.parseColor("#a35567"));
        colors.add(Color.parseColor("#ff5f67"));
        colors.add(Color.parseColor("#3ca567"));*/

    }

    private void showPieChart(){

        //????????? ???/??? ??????
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for(String type : datalist.keySet()){
            pieEntries.add(new PieEntry(datalist.get(type).floatValue(), type));
            ex_datalist.put(type, datalist.get(type));
        }

        //????????? ??????, ???????????? ??????, ?????? ??????
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"");
        pieDataSet.setValueTextSize(12f);
        pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        //pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //pieDataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

        //data??? ?????????
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true); //value?????? ??????
        pieData.setValueFormatter(new PercentFormatter(pieChart_country)); //???????????????

        //???????????????
        Legend l = pieChart_country.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);

        pieChart_country.setCenterText("??????");
        pieChart_country.setCenterTextSize(24);
        pieChart_country.setDrawHoleEnabled(true);
        pieChart_country.setHoleColor(Color.parseColor("#f0ffff"));
        pieChart_country.setUsePercentValues(true); //???????????? ??????
        pieChart_country.setDescription(null);
        pieChart_country.setEntryLabelColor(Color.BLACK);
        pieChart_country.setData(pieData);
        pieChart_country.invalidate();

        pieChart_country.animateY(1400, Easing.EaseInOutQuad);
        pieChart_country.setRotationEnabled(false);
    }
}