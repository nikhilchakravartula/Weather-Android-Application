package services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

import models.Address;

public class IPInfoService {
    final String URL = "https://www.ipinfo.io/?token=";
    Context context;

    public IPInfoService(Context context) {
        this.context = context;
    }

    public interface IPInfoListener {
        void onError(String message);
        void onResponse(Address address);
    }

    public void getIPInfo(IPInfoListener listener) {
        JsonObjectRequest locationReq = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Address address  = new Address(response);
                listener.onResponse(address);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError("IP Info Failed restart app");
            }
        });
        locationReq.setShouldCache(false);
        Volley.getInstance(context).addToRequestQueue(locationReq);
    }
}
