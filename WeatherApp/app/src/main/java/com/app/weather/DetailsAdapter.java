package com.app.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import models.Weather;

public class DetailsAdapter extends FragmentStateAdapter {
    private Weather weather;

    public DetailsAdapter(FragmentManager fm, Lifecycle lifecycle, Weather weather) {
        super(fm, lifecycle);
        this.weather = weather;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (position)
        {
            case 0:
            fragment = new TodayTabFragment();
            bundle.putSerializable(TodayTabFragment.DATA_OBJ, weather.getCurrent());
            break;

            case 1:
            fragment = new WeeklyTabFragment();
            bundle.putSerializable(WeeklyTabFragment.DATA_OBJ,weather.getWeekly());
            break;
            case 2:
            fragment = new WeatherDataTabFragment();
            bundle.putSerializable(WeatherDataTabFragment.DATA_OBJ,weather.getCurrent());
            break;
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
