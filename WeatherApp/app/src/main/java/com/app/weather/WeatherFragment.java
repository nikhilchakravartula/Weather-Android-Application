package com.app.weather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import models.Current;
import models.Weather;
import models.Weekly;
import models.constants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {
    Weather weatherData;

    public boolean getFavChangedTo() {
        return favChangedTo;
    }

    private FloatingActionButton add_fav;
    private FloatingActionButton rem_fav;
    private boolean showFab =true;
    private SharedPreferences sharedPref;
    private boolean favChangedTo=true;
    private boolean isFav = true;
    public WeatherFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static WeatherFragment newInstance(Weather weather, boolean showFab,boolean isFav) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("weatherData",weather);
        fragment.setArguments(bundle);
        fragment.showFab = showFab;
        fragment.favChangedTo = isFav;
        fragment.isFav = isFav;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.weatherData = (Weather) getArguments().get("weatherData");
        }
        sharedPref = getActivity().getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //Log.d("onCreateView fragment",""+R.id.current_weather_icon);
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        setFavs(view);
        setCurrentWeatherView(view,this.weatherData.getCurrent());
        setCurrentFieldsView(view,this.weatherData.getCurrent());
        setWeeklyView(view,weatherData.getWeekly());
        setResources(view);
        return view;
    }

    public boolean isFav() {
        return isFav;
    }

    private void setFavs(View view)
    {

        //Log.d("SET FAVS ",""+showFab);
        add_fav = view.findViewById(R.id.add_fab);
        rem_fav = view.findViewById(R.id.rem_fab);
        if(!showFab)
        {
            add_fav.setVisibility(View.GONE);
            rem_fav.setVisibility(View.GONE);
            return;
        }
        if(isFav)
        {
            add_fav.setVisibility(View.GONE);
            rem_fav.setVisibility(View.VISIBLE);
        }
        else
        {
            rem_fav.setVisibility(View.GONE);
            add_fav.setVisibility(View.VISIBLE);

        }
        add_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionBar bar = ((AppCompatActivity)getActivity()).getSupportActionBar();
                add_fav.setVisibility(View.GONE);
                rem_fav.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(weatherData.getAddress().toString(), true);
                editor.apply();
                favChangedTo = true;
                Toast.makeText(getActivity().getApplicationContext(), bar.getTitle()+" was added to favorites ",Toast.LENGTH_SHORT).show();
            }
        });


        rem_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rem_fav.setVisibility(View.GONE);
                add_fav.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove(weatherData.getAddress().toString());
                editor.apply();
                favChangedTo = false;
                Toast.makeText(getActivity().getApplicationContext(), weatherData.getAddress().getCity()+", "+weatherData.getAddress().getState() +" was removed from favorites ",Toast.LENGTH_SHORT).show();
                if(getActivity() instanceof MainActivity)
                {
                    ((MainActivity)getActivity()).onRemoveFav(weatherData);
                }
            }
        });
    }
    private void setCurrentWeatherView(View view,Current currentWeather)
    {
        View summaryView = view.findViewById(R.id.weather_card_summary);
        ((ImageView)summaryView.findViewById(R.id.current_weather_icon)).setImageResource(constants.imageIds.get(currentWeather.getWeatherCode()));
        ((TextView)summaryView.findViewById(R.id.current_temperature)).setText(""+(int)currentWeather.getTemperature()+"°F");
        ((TextView)summaryView.findViewById(R.id.current_status)).setText(constants.statusIds.get(currentWeather.getWeatherCode()));
        ((TextView)summaryView.findViewById(R.id.current_address)).setText(weatherData.getAddress().getCity()+", "+weatherData.getAddress().getState());
        summaryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DetailsActivity.class);
                intent.putExtra("weatherData",weatherData);
                startActivity(intent);
            }
        });
    }

    private void setCurrentFieldsView(View view, Current currentWeather)
    {
        View fieldsView = view.findViewById(R.id.weather_card_fields);
        ((TextView)fieldsView.findViewById(R.id.humidity).findViewById(R.id.field_value)).setText(currentWeather.getHumidity()+"%");
        ((TextView)fieldsView.findViewById(R.id.wind_speed).findViewById(R.id.field_value)).setText(currentWeather.getWindSpeed()+"mph");
        ((TextView)fieldsView.findViewById(R.id.visibility).findViewById(R.id.field_value)).setText(currentWeather.getVisibility()+"mi");
        ((TextView)fieldsView.findViewById(R.id.pressure).findViewById(R.id.field_value)).setText(currentWeather.getPressureSeaLevel()+"inHg");
    }

    private void setWeeklyView(View view, Weekly weeklyWeather)
    {
        WeekListAdapter adapter = new WeekListAdapter(getActivity(),R.layout.weather_card_week,weeklyWeather.getWeeklyData());
        ((ListView)view.findViewById(R.id.weather_card_week_lv)).setAdapter(adapter);
    }

    private static void setResources(View view)
    {
        setImageResources(view);
        setTextResources(view);
    }
    private static void setImageResources(View view)
    {
        ((ImageView) (view.findViewById(R.id.wind_speed).findViewById(R.id.field_icon))).setImageResource(R.drawable.wind_speed);
        ((ImageView) (view.findViewById(R.id.humidity).findViewById(R.id.field_icon))).setImageResource(R.drawable.humidity);
        ((ImageView) (view.findViewById(R.id.visibility).findViewById(R.id.field_icon))).setImageResource(R.drawable.visibility);
        ((ImageView) (view.findViewById(R.id.pressure).findViewById(R.id.field_icon))).setImageResource(R.drawable.pressure);
    }

    private static void setTextResources(View view )
    {
        ((TextView)(view.findViewById(R.id.humidity).findViewById(R.id.field_name))).setText(R.string.humidity);
        ((TextView)(view.findViewById(R.id.wind_speed).findViewById(R.id.field_name))).setText(R.string.wind_speed);
        ((TextView)(view.findViewById(R.id.pressure).findViewById(R.id.field_name))).setText(R.string.pressure);
        ((TextView)(view.findViewById(R.id.visibility).findViewById(R.id.field_name))).setText(R.string.visibility);
    }


}