package services;

import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

import models.Address;

public class GeoCodingService {
    final String URL = "https://maps.googleapis.com/maps/api/geocode/json?key=key&address=";
    Context context;

    public GeoCodingService(Context context) {
        this.context = context;
    }

    public interface GeoCodingListener {
        void onError(String message);
        void onResponse(Address address);
    }

    public void getGeoCoding(GeoCodingListener listener,String addr) {
        StringBuilder url = new StringBuilder(URL);
        url.append(Uri.encode(addr));
        Log.d("GEOCODING SERVICE URL",url.toString());
        JsonObjectRequest geoCodingReq = new JsonObjectRequest(Request.Method.GET, url.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Address address  = new Address(response);
                listener.onResponse(address);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError("Geocoding Failed restart app");
            }
        });

        geoCodingReq.setShouldCache(false);
        Volley.getInstance(context).addToRequestQueue(geoCodingReq);
    }

}
