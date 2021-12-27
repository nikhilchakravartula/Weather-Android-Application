package models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Daily implements Serializable {
    double temperature			;
    double temperatureApparent	;
    double temperatureMin		;
    double temperatureMax		;
    double windSpeed				;
    double windDirection			;
    int humidity				;
    double pressureSeaLevel		;
    int weatherCode				;
    int precipitationProbability;
    int precipitationType		;
    double visibility			;
    int moonPhase;
    int cloudCover				;
    OffsetDateTime startDateTimeOffset;



    @RequiresApi(api = Build.VERSION_CODES.O)
    Daily(JSONObject interval)
    {
        try
        {
            this.startDateTimeOffset = OffsetDateTime.parse(interval.getString("startTime")
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
            this.weatherCode = values.getInt("weatherCode");
            this.precipitationProbability = values.getInt("precipitationProbability");
            this.precipitationType = values.getInt("precipitationType");
            this.visibility = values.getDouble("visibility");
            this.cloudCover = values.getInt("cloudCover");
            this.moonPhase = values.getInt("moonPhase");
        }
        catch (JSONException jse)
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

    public int getMoonPhase() {
        return moonPhase;
    }

    public void setMoonPhase(int moonPhase) {
        this.moonPhase = moonPhase;
    }

    public int getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(int cloudCover) {
        this.cloudCover = cloudCover;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getStartDate() {
        return startDateTimeOffset.toLocalDate().toString();
    }

    public void setStartDateTimeOffset(OffsetDateTime startDateTimeOffset) {
        this.startDateTimeOffset = startDateTimeOffset;
    }

    public OffsetDateTime getStartDateTimeOffset()
    {
        return startDateTimeOffset;
    }

}
