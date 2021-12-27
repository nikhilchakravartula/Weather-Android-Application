package com.app.weather;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import models.Address;
import models.Weather;
import services.AutoCompleteService;
import services.IPInfoService;
import services.WeatherService;

public class MainActivity extends AppCompatActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private WeatherPagerAdapter pagerAdapter;

    private SearchView searchView;
    private Menu menu;
    private Toolbar myToolbar;
    LinearLayoutCompat dots;
    private final IPInfoService ipInfoService = new IPInfoService(MainActivity.this);
    private final WeatherService weatherService = new WeatherService(MainActivity.this);
    private final AutoCompleteService autoCompleteService = new AutoCompleteService(MainActivity.this);
    private View progressBar;
    private View container;
    ArrayAdapter<Address> suggestionsAdapter;
    private SharedPreferences sharedPref ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();
        initViews();
        setListeners();
        getCurrentWeather();
    }

    private void initViews()
    {
        (progressBar=findViewById(R.id.progress_bar)).setVisibility(View.VISIBLE);
        (container=findViewById(R.id.container)).setVisibility(View.GONE);
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new WeatherPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        dots = findViewById(R.id.dots);
        suggestionsAdapter = new ArrayAdapter<Address>(this,
                android.R.layout.simple_list_item_1,
                new ArrayList<Address>());
        //querySuggestions.setAdapter(suggestionsAdapter);

    }

    private void customOnPageSelected(int position)
    {
        Log.d("ON PAGE CHANGE","") ;
        for(int i=0;i<dots.getChildCount();i++){
            ((TextView)dots.getChildAt(i)).setTextColor(getResources().getColor(R.color.weather_fragment_bg));
        }
        ((TextView)dots.getChildAt(position)).setTextColor(getResources().getColor(R.color.white));

    }
    private void setListeners()
    {
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                customOnPageSelected(position);
            }
        });
    }

    private void getCurrentWeather()
    {
        progressBar.setVisibility(View.VISIBLE);
        ipInfoService.getIPInfo(new IPInfoService.IPInfoListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(Address address) {
                Toast.makeText(MainActivity.this,address.getLatitude()+" "+address.getLongitude(),Toast.LENGTH_SHORT);
                // Call Backend API to get data
                getWeather(address);
            }
        });
    }

    private void getWeather(Address address)
    {
        weatherService.getWeather(new WeatherService.WeatherListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                //Display Error page
            }

            @Override
            public void onResponse(Weather weather) {
                pagerAdapter.add(weather);
                pagerAdapter.notifyDataSetChanged();
                container.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

            }
        },address);
    }
    private void addDot()
    {
        TextView tv;
        (tv=new TextView(MainActivity.this)).setText(Html.fromHtml("&#9679;"));
        tv.setTextColor(getResources().getColor(R.color.weather_fragment_bg));
        tv.setTextSize(20);
        tv.setPadding(10,10,10,10);
        dots.addView(tv);
    }
    private void removeDot()
    {
        dots.removeViewAt(0);
    }
    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        searchView = (SearchView) searchItem.getActionView();
        SearchView.SearchAutoComplete searchAutoComplete =
                (SearchView.SearchAutoComplete)searchView.findViewById(
                androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setAdapter(suggestionsAdapter);
//        searchAutoComplete.setDropDownBackgroundDrawable(
//                getResources().getDrawable(androidx.appcompat.R.drawable.abc_popup_background_mtrl_mult));
        Log.d("SEARCHAUTO",searchAutoComplete+"");

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query)
                    {
                        Intent intent=new Intent(MainActivity.this,Searchable.class);
                        intent.putExtra("query",query);
                        startActivityForResult(intent,0);
                        return false;
                    }

                    // This method is overridden to filter
                    // the adapter according to a search query
                    // when the user is typing search
                    @Override
                    public boolean onQueryTextChange(String newText)
                    {

                        autoCompleteService.getSuggestions(new AutoCompleteService.AutoCompleteListener() {
                            @Override
                            public void onError(String message) {
                                Log.d("AUTOCOMPLETE","FAIL");
                            }

                            @Override
                            public void onResponse(ArrayList<Address> data) {
                                suggestionsAdapter.clear();
                                suggestionsAdapter.addAll(data);
                            }
                        },newText);

                        return false;
                    }
                });
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchAutoComplete.setText(""+parent.getItemAtPosition(position));
                searchView.setQuery(searchView.getQuery(),true);
            }
        });
        //searchView.setSuggestionsAdapter();
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(new ComponentName(this,Searchable.class)));
//        searchView.clearFocus();

        Log.d("LOG", "onCreateOptionsMenu:"+ getComponentName());

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        myToolbar.collapseActionView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("OnOptionsItem selected","");
        switch (item.getItemId()) {
            case R.id.app_bar_search:
                // User chose the "Settings" item, show the app settings UI...
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            Boolean addToFav = data.getBooleanExtra("addToFav",false);
            Boolean removeFromFav = data.getBooleanExtra("removeFromFav",false);
            Toast.makeText(this,""+addToFav,Toast.LENGTH_LONG);
            Weather weather = (Weather)data.getSerializableExtra("weatherData");
            Log.d("======",addToFav+"");
            if(addToFav)
                pagerAdapter.add(weather);
            if(removeFromFav)
                pagerAdapter.remove(weather);
        }
    }

    public void onRemoveFav(Weather weather)
    {
        pagerAdapter.remove(weather);
    }

    /**x
     * A simple pager adapter that represents 5 WeatherPagerAdapter objects, in
     * sequence.
     */
    private class WeatherPagerAdapter extends FragmentStateAdapter {

        LinkedList<Weather> weatherList;
        public WeatherPagerAdapter(FragmentActivity fa) {
            super(fa);
            this.weatherList = new LinkedList<Weather>();
        }

        @Override
        public Fragment createFragment(int position) {
            WeatherFragment fragment ;
            Map<String,?> pref = sharedPref.getAll();
            for(Map.Entry<String,?> entry:pref.entrySet())
            {
                Log.d("PREFERENCE============",entry.getKey()+", "+entry.getValue());
            }

            if(position ==0)
                fragment = WeatherFragment.newInstance(weatherList.get(position),false,false);
            else
            {

                if(sharedPref.getBoolean(weatherList.get(position).getAddress().toString(),false))
                {
                    fragment = WeatherFragment.newInstance(weatherList.get(position), true, true);
                }
                else
                {
                    fragment = WeatherFragment.newInstance(weatherList.get(position), true, false);
                }
            }
            return fragment;
        }

        @Override
        public int getItemCount() {
            return this.weatherList.size();
        }

        public void add(Weather weather)
        {
            this.weatherList.add(weather);
            addDot();
            notifyDataSetChanged();
        }

        public void remove(Weather weather)
        {
            for(int i=0;i<weatherList.size();i++)
            {
                Weather w = weatherList.get(i);
                if(w.getAddress().toString().equalsIgnoreCase(weather.getAddress().toString()))
                {
                    remove(i);
                }
            }
        }
        public void remove(int position)
        {
            weatherList.remove(position);
            removeDot();
            notifyItemRangeChanged(position, weatherList.size());
                viewPager.setCurrentItem(position-1);
                customOnPageSelected(position-1);
            notifyDataSetChanged();

        }

        @Override
        public long getItemId(int position) {
            return weatherList.get(position).hashCode(); // make sure notifyDataSetChanged() works
        }
        public boolean containsItem(long itemId) {
            for(Weather weather:weatherList)
            {
                if(weather.hashCode()==itemId)
                    return true;
            }
            return false;
        }
    }
}