package models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Weekly implements Serializable {
    ArrayList<Daily> weeklyData;
    OffsetDateTime startDateTimeOffset;
    public ArrayList<Daily> getWeeklyData() {
        return weeklyData;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    Weekly(JSONObject weeklyObj) {
        this.weeklyData = new ArrayList<Daily>();
        try {
            JSONObject data = weeklyObj.getJSONObject("data");
            JSONArray timelines = data.getJSONArray("timelines");
            JSONObject timeline = (JSONObject) timelines.get(0);
            startDateTimeOffset = OffsetDateTime.parse(timeline.getString("startTime")
                    , DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            JSONArray intervals = timeline.getJSONArray("intervals");
            for (int i = 0; i < intervals.length(); i++) {
                JSONObject interval = intervals.getJSONObject(i);
                weeklyData.add(new Daily(interval));
            }
        }
        catch (JSONException jse)
        {
            jse.printStackTrace();
        }
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public String getDate()
        {
            return startDateTimeOffset.toLocalDate().toString();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public long startDateEpoch()
        {
            return startDateTimeOffset.toEpochSecond()*1000;
        }
}
