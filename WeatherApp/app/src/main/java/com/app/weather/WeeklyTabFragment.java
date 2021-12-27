package com.app.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.highsoft.highcharts.common.HIColor;
import com.highsoft.highcharts.common.HIGradient;
import com.highsoft.highcharts.common.HIStop;
import com.highsoft.highcharts.common.hichartsclasses.*;
import com.highsoft.highcharts.core.*;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import models.Daily;
import models.Weekly;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeeklyTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeeklyTabFragment extends Fragment {


    public static final String DATA_OBJ = "weeklyWeather";
    private Weekly weeklyWeather;

    public WeeklyTabFragment() {
        // Required empty public constructor
    }

    public static WeeklyTabFragment newInstance(Weekly weeklyWeather) {
        WeeklyTabFragment fragment = new WeeklyTabFragment();
        Bundle args = new Bundle();
        args.putSerializable(DATA_OBJ,weeklyWeather);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            this.weeklyWeather = (Weekly) args.getSerializable(DATA_OBJ);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weekly_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View loading = view.findViewById(R.id.loadingView);
        loading.setVisibility(View.VISIBLE);
        HIChartView dailyChart = (HIChartView) view.findViewById(R.id.dailyChart);

        try {
            draw(dailyChart);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        loading.setVisibility(View.GONE);
    }

    public void draw(HIChartView chartView) throws JSONException {

        HIOptions options = new HIOptions();

        HIChart chart = new HIChart();
        chart.setType("arearange");
        chart.setZoomType("x");
        options.setChart(chart);
        HITitle title = new HITitle();
        title.setText("Temperature variation by day");
        HICSSObject titleCSS = new HICSSObject();
        titleCSS.setColor("#716f6f");
        titleCSS.setFontWeight("bold");
        titleCSS.setFontSize("16");
        title.setStyle(titleCSS);
        options.setTitle(title);
        HIXAxis xaxis = new HIXAxis();
        xaxis.setType("datetime");
        xaxis.setTickInterval(24*3600*1000);
        options.setXAxis(new ArrayList<HIXAxis>(){{add(xaxis);}});

        HIYAxis yaxis = new HIYAxis();
        yaxis.setTitle(new HITitle());
        options.setYAxis(new ArrayList<HIYAxis>(){{add(yaxis);}});

        HITooltip tooltip = new HITooltip();
        tooltip.setShadow(true);
        tooltip.setShared(true);
        tooltip.setValueSuffix("°F");
        tooltip.setXDateFormat("%Y-%m-%d");
        options.setTooltip(tooltip);

        HILegend legend = new HILegend();
        legend.setEnabled(false);
        options.setLegend(legend);

        HIArearange series = new HIArearange();
        series.setName("Temperatures");
        HIGradient g = new HIGradient(0,0,0,1);
        LinkedList<HIStop> l = new LinkedList<>();
        l.add(new HIStop(0, HIColor.initWithRGBA(242,144,69, 0.70)));
        l.add(new HIStop(1, HIColor.initWithRGBA(152,182,220,1)));
        series.setColor(HIColor.initWithLinearGradient(g, l));
        HIMarker m = new HIMarker();
        m.setEnabled(true);
        series.setMarker(m);
        series.setPointStart(weeklyWeather.startDateEpoch());
        series.setPointInterval(24*3600*1000);
        ArrayList<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
        for(Daily dailyWeather:weeklyWeather.getWeeklyData())
        {
            data.add(new ArrayList<Double>());
            data.get(data.size()-1).add(dailyWeather.getTemperatureMin());
            data.get(data.size()-1).add(dailyWeather.getTemperatureMax());
        }
        series.setData(data);
        options.setSeries(new ArrayList<>(Arrays.asList(series)));
        chartView.setOptions(options);
    }
}