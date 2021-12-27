package services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

public class Volley {

    private static Volley instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private Volley(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized Volley getInstance(Context context) {
        if (instance == null) {
            instance = new Volley(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(ctx.getApplicationContext());
        }
        //
        requestQueue.getCache().clear();
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
