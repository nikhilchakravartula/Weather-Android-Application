package models;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.temporal.WeekFields;
import java.util.ArrayList;

public class Weather implements Serializable {
    private Address address;
    private Current current;
    //private Hourly hourly;
    private Weekly weekly;

    public Address getAddress() {
        return address;
    }

    public Current getCurrent() {
        return current;
    }

    public Weekly getWeekly() {
        return weekly;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Weather(JSONObject weatherObj)
    {
        try {
            JSONObject currentObj = weatherObj.getJSONObject("current");
            JSONObject weeklyObj = weatherObj.getJSONObject("daily");
            this.current = new Current(currentObj);
            this.weekly = new Weekly(weeklyObj);
        }
        catch(JSONException jse)
        {
            jse.printStackTrace();
        }
    }

}
