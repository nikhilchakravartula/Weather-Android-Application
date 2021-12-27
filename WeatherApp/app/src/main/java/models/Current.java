package models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Current implements Serializable {

    double temperature			;
    double temperatureApparent	;
    double temperatureMin		;
    double temperatureMax		;
    double windSpeed				;
    double windDirection			;
    int humidity				;
    double pressureSeaLevel		;
    int uvIndex					;
    int weatherCode				;
    int precipitationProbability;
    int precipitationType		;
    double visibility			;
    int cloudCover				;
    OffsetDateTime startDateTime;

    @RequiresApi(api = Build.VERSION_CODES.O)
    Current(JSONObject currentObj)
    {
        try {
            JSONObject data = currentObj.getJSONObject("data");
            JSONArray timelines = data.getJSONArray("timelines");
            JSONObject timeline = (JSONObject) timelines.get(0);
            JSONArray intervals = timeline.getJSONArray("intervals");
            JSONObject interval = intervals.getJSONObject(0);
            this.startDateTime = OffsetDateTime.parse(interval.getString("startTime")
                    , DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            JSONObject values = interval.getJSONObject("values");
            this.temperature = values.getDouble("temperature");
            this.temperatureApparent = values.getDouble("temperatureApparent");
            this.temperatureMin = values.getDouble("temperatureMin");
            this.temperatureMax = values.getDouble("temperatureMax");
            this.windSpeed = values.getDouble("windSpeed");
            this.windDirection = values.getDouble("windDirection");
            this.humidity = values.getInt("humidity");
            this.pressureSeaLevel = values.getDouble("pressureSeaLevel");
            this.uvIndex = values.getInt("uvIndex");
            this.weatherCode = values.getInt("weatherCode");
            this.precipitationProbability = values.getInt("precipitationProbability");
            this.precipitationType = values.getInt("precipitationType");
            this.visibility = values.getDouble("visibility");
            this.cloudCover = values.getInt("cloudCover");
        }
        catch(JSONException jse)
        {
            jse.printStackTrace();
        }
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperatureApparent() {
        return temperatureApparent;
    }

    public void setTemperatureApparent(double temperatureApparent) {
        this.temperatureApparent = temperatureApparent;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getPressureSeaLevel() {
        return pressureSeaLevel;
    }

    public void setPressureSeaLevel(double pressureSeaLevel) {
        this.pressureSeaLevel = pressureSeaLevel;
    }

    public int getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(int uvIndex) {
        this.uvIndex = uvIndex;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }

    public int getPrecipitationProbability() {
        return precipitationProbability;
    }

    public void setPrecipitationProbability(int precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    public int getPrecipitationType() {
        return precipitationType;
    }

    public void setPrecipitationType(int precipitationType) {
        this.precipitationType = precipitationType;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public int getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(int cloudCover) {
        this.cloudCover = cloudCover;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getStartDate()
    {
        return startDateTime.toLocalDate().toString();
    }
    public OffsetDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(OffsetDateTime startDateTime) {
        this.startDateTime= startDateTime;
    }


}
