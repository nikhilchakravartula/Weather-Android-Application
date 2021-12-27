package models;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Address implements Serializable {
    public Address()
    {

    }
    public Address(@NonNull JSONObject addressObj)
    {
        try {
            if(addressObj.has("results"))
            {
                JSONObject result = (JSONObject) addressObj.getJSONArray("results").get(0);
                String addressStr = result.getString("formatted_address");
                String[] addressTokens = addressStr.split(",");
                this.city = addressTokens[0];
                this.state = addressTokens[1];
                this.country = addressTokens[2];
                JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
                this.latitude  =  location.getDouble("lat")+"";
                this.longitude = location.getDouble("lng")+"";
            }
            else if(addressObj.has("city")){
                    this.city = addressObj.getString("city");
                    this.state = addressObj.getString("region");
                    this.latitude = addressObj.getString("loc").split(",")[0];
                    this.longitude = addressObj.getString("loc").split(",")[1];
                    this.country = addressObj.getString("country");
            }
        }
        catch(JSONException jse)
        {
            jse.printStackTrace();
        }
    }
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @NonNull
    @Override
    public String toString() {
        return city+", "+state;
    }

    private String latitude;
    private String longitude;
    private String city;
    private String state;
    private String country;
    private String street;


}
