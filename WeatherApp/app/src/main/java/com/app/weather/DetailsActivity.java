package com.app.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONException;
import org.json.JSONObject;

import models.Weather;

public class DetailsActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private DetailsAdapter detailsAdapter;
    private Weather weatherData;
    Toolbar myToolbar;
    private Menu menu;
    private void setViews()
    {
        viewPager = findViewById(R.id.tabPager);
        tabLayout = findViewById(R.id.tabLayout);
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(weatherData.getAddress().getCity()+", "+weatherData.getAddress().getState());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
            this.weatherData = (Weather)
                    getIntent().getSerializableExtra("weatherData");
        setViews();
        detailsAdapter = new DetailsAdapter(getSupportFragmentManager(),getLifecycle(),weatherData);
        viewPager.setAdapter(detailsAdapter);
        viewPager.setOffscreenPageLimit(3);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch(position) {
                case 0:
                tab.setText("TODAY");
                tab.setIcon(R.drawable.today);
                break;
                case 1:
                tab.setText("WEEKLY");
                tab.setIcon(R.drawable.weekly_tab);
                break;
                case 2:
                tab.setText("WEATHER DATA");
                tab.setIcon(R.drawable.weather_data_tab);
                break;
            }
        }).attach();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.details_menu, menu);
        MenuItem twitterButton = menu.findItem(R.id.twitter);
        twitterButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String url = "https://twitter.com/intent/tweet?text="+Uri.encode(
                        "Check out "+
                        weatherData.getAddress().getCity()
                        + ", "
                        + weatherData.getAddress().getState()
                        +", "
                        +"USA's weather! It is "
                        +weatherData.getCurrent().getTemperature()+"°F!")
                        +"&hashtags=CSCI571WeatherForecast";


                url = url.replace("'","%27");
                Log.d("URL",url);
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(intent);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

}