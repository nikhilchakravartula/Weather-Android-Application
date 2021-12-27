package com.app.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import models.Address;
import models.Weather;
import services.GeoCodingService;
import services.WeatherService;

public class Searchable extends AppCompatActivity {

    private final WeatherService weatherService = new WeatherService(Searchable.this);
    private final GeoCodingService geoCodingService = new GeoCodingService(Searchable.this);
    Weather weatherData;
    private View progressBar;
    private View container;
    private WeatherFragment fragment;
    private SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        progressBar = findViewById(R.id.progress_bar);
        container = findViewById(R.id.container);
        progressBar.setVisibility(View.VISIBLE);
        container.setVisibility(View.GONE);
        sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        setToolbar();
        handleIntent(getIntent());
    }

    private void getWeather(Address address)
    {
        weatherService.getWeather(new WeatherService.WeatherListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(Searchable.this, message, Toast.LENGTH_SHORT).show();
                //Display Error page
            }

            @Override
            public void onResponse(Weather weather) {
                weatherData = weather;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("weatherData",weather);
                    fragment = WeatherFragment.newInstance(weather,true,sharedPref.getBoolean(weather.getAddress().toString(),false));
                    fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .add(R.id.fragment_container_view, fragment)
                            .commit();
                container.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        },address);
    }

    private void getGeoCoding(String addr)
    {
        geoCodingService.getGeoCoding(new GeoCodingService.GeoCodingListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(Searchable.this, message, Toast.LENGTH_SHORT).show();
                //Display Error page
            }

            @Override
            public void onResponse(Address address) {
                getWeather(address);
            }
        },addr);
    }

    private void setToolbar()
    {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setBackgroundResource(R.color.card_bg);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(SearchManager.QUERY));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent();
                if(fragment.getFavChangedTo()==true && fragment.isFav()==false)
                {
                    intent.putExtra("addToFav",true);
                }
                else if(fragment.getFavChangedTo()==false && fragment.isFav()==true)
                {
                    intent.putExtra("removeFromFav",true);
                }
                setResult(RESULT_OK,intent);
                intent.putExtra("weatherData",weatherData);
                finish();
                return true;
        }
        return true;
    }

    public void onRemoveFav(Weather weather)
    {
        ((MainActivity)getParent()).onRemoveFav(weatherData);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("ONCREATEINTENT","Called");
        handleIntent(intent);
    }
    private void handleIntent(Intent intent)
    {
//        if(Intent.ACTION_SEARCH.equalsIgnoreCase(intent.getAction()))
//        {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            Toast.makeText(this,query,Toast.LENGTH_LONG).show();
//            getGeoCoding(query);
//        }
        getGeoCoding(intent.getStringExtra("query"));
    }
}