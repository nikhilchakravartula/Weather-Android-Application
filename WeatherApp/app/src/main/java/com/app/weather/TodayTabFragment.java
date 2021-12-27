package com.app.weather;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import models.Current;
import models.constants;

public class TodayTabFragment extends Fragment {
    public static final String DATA_OBJ = "data";
    private final int NUM_CELLS = 9;
    GridViewAdapter gridViewAdapter;
    public TodayTabFragment() {
        // Required empty public constructor
    }
    public static TodayTabFragment newInstance(Current currentWeather) {
        TodayTabFragment fragment = new TodayTabFragment();
        Bundle args = new Bundle();
        args.putSerializable(DATA_OBJ,currentWeather);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            gridViewAdapter = new GridViewAdapter(getContext(),(Current) args.getSerializable(DATA_OBJ));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today_tab, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridView gv = view.findViewById(R.id.grid_view);
        gv.setAdapter(gridViewAdapter);
    }

    private class GridViewAdapter extends BaseAdapter
    {
        Current currentWeather;
        Context context;
        View view;
        LayoutInflater layoutInflater;

        public GridViewAdapter(Context context, Current currentWeather) {
            this.context = context;
            this.currentWeather = currentWeather;
        }

        @Override
        public int getCount() {
            return NUM_CELLS;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = new View(context);
            view = layoutInflater.inflate(R.layout.grid_cell,null);
            ImageView gridCellIcon = view.findViewById(R.id.grid_cell_icon);
            TextView gridCellValue = view.findViewById(R.id.grid_cell_value);
            TextView gridCellName = view.findViewById(R.id.grid_cell_name);

            switch(position) {
                case 0:
                    gridCellIcon.setImageResource(R.drawable.wind_speed);
                    gridCellValue.setText(currentWeather.getWindSpeed()+"mph");
                    gridCellName.setText(R.string.wind_speed);
                    break;
                case 1:
                    gridCellIcon.setImageResource(R.drawable.pressure);
                    gridCellValue.setText(currentWeather.getPressureSeaLevel()+"inHg");
                    gridCellName.setText(R.string.pressure);
                    break;
                case 2:
                    gridCellIcon.setImageResource(R.drawable.weather_pouring);
                    gridCellValue.setText(currentWeather.getPrecipitationProbability()+"%");
                    gridCellName.setText(R.string.precipitation);
                    break;

                case 3:
                    gridCellIcon.setImageResource(R.drawable.weather_data_tab);
                    gridCellValue.setText((int)currentWeather.getTemperature()+"°F");
                    gridCellName.setText(R.string.temperature);
                    break;

                case 4:
                    gridCellIcon.setImageResource(constants.imageIds.get(currentWeather.getWeatherCode()));
                    gridCellValue.setText(constants.statusIds.get(currentWeather.getWeatherCode()));
                    break;

                case 5:
                    gridCellIcon.setImageResource(R.drawable.humidity);
                    gridCellValue.setText(currentWeather.getHumidity()+"%");
                    gridCellName.setText(R.string.humidity);
                    break;

                case 6:
                    gridCellIcon.setImageResource(R.drawable.visibility);
                    gridCellValue.setText(currentWeather.getVisibility()+"mi");
                    gridCellName.setText(R.string.visibility);
                    break;

                case 7:
                    gridCellIcon.setImageResource(R.drawable.cloud_cover);
                    gridCellValue.setText(currentWeather.getCloudCover()+"%");
                    gridCellName.setText(R.string.cloud_cover);
                    break;

                case 8:
                    gridCellIcon.setImageResource(R.drawable.uv);
                    gridCellValue.setText(currentWeather.getUvIndex()+"");
                    gridCellName.setText(R.string.ozone);
                    break;
            }

            return view;
        }
    }
}