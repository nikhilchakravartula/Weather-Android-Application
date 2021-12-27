package com.app.weather;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

import models.Daily;
import models.Weekly;
import models.constants;

class WeekListAdapter extends ArrayAdapter<Daily>
{
    private Context mContext;
    private  int mResource;
    public WeekListAdapter(Context context, int resource, ArrayList<Daily> weeklyData)
    {
        super(context,resource,weeklyData);
        mContext = context;
        mResource = resource;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(mResource,parent,false);
        Daily dailyData = getItem(position);
        ((TextView) convertView.findViewById(R.id.date)).setText(dailyData.getStartDate());
        ((ImageView) convertView.findViewById(R.id.weather_icon)).setImageResource(constants.imageIds.get(dailyData.getWeatherCode()));
        ((TextView) convertView.findViewById(R.id.temperature_min)).setText(""+(int)dailyData.getTemperatureMin());
        ((TextView) convertView.findViewById(R.id.temperature_max)).setText(""+(int)dailyData.getTemperatureMax());
        return convertView;
        //return super.getView(position, convertView, parent);
    }
}
