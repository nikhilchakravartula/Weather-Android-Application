package services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import models.Address;

public class AutoCompleteService {
    final String URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?key=key&components=country:us&types=(cities)&input=";
    Context context;

    public AutoCompleteService(Context context) {
        this.context = context;
    }

    public interface AutoCompleteListener {
        void onError(String message);
        void onResponse(ArrayList<Address> data);
    }

    public void getSuggestions(AutoCompleteListener listener,String input) {
        String url = URL;
        url+=input;
        JsonObjectRequest locationReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<Address> data = new ArrayList<Address>();
                try {
                    JSONArray predictions = response.getJSONArray("predictions");
                    for(int i=0;i<predictions.length();i++)
                    {
                        JSONArray terms = ((JSONObject)(predictions.get(i))).getJSONArray("terms");
                        String city = ((JSONObject)terms.get(0)).getString("value");
                        String state = ((JSONObject)terms.get(1)).getString("value");
                        String country = ((JSONObject)terms.get(2)).getString("value");
                        Address address = new Address();
                        address.setCity(city);
                        address.setCountry(country);
                        address.setState(state);
                        data.add(address);
                    }
                }
                catch(JSONException jse)
                {
                    jse.printStackTrace();
                }
                listener.onResponse(data);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError("AutoCompleteSuggestions failed. Restart app");
            }
        });
        locationReq.setShouldCache(false);
        Volley.getInstance(context).addToRequestQueue(locationReq);
    }
}
