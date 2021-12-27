package services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import models.Address;
import models.Weather;

public class WeatherService {

    final String URL = "backend_url";
    Context ctx;

    public WeatherService(Context context) {
        ctx=context;
    }

    public interface WeatherListener {
        void onError(String message);
        void onResponse(Weather weather);
    }

    public void getWeather(WeatherListener weatherListener, Address address) {
        StringBuilder url = new StringBuilder(URL);
        url.append("latitude=");
        url.append(address.getLatitude());
        url.append("&");
        url.append("longitude=");
        url.append(address.getLongitude());
        Log.d("WEATHERSERVICEURL",url.toString());
        JsonObjectRequest weatherRequest = new JsonObjectRequest(Request.Method.GET,url.toString(),
                null,
                 new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Weather weather = new Weather(response);
                weather.setAddress(address);
                weatherListener.onResponse(weather);
                // handle error when we get query limit exceed
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                weatherListener.onError("Requests exhausted ");
                Log.d("getWeather","Volley error"+error);
            }
        });
        weatherRequest.setShouldCache(false);
        Volley.getInstance(ctx).addToRequestQueue(weatherRequest);
    }
}
