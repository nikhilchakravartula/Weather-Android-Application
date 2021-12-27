package models;

import com.app.weather.R;

import java.util.HashMap;
import java.util.Map;

public class constants {
    public static Map<Integer,Integer> imageIds;
    public static HashMap<Integer,Integer> statusIds;
    static
    {
        imageIds = new HashMap<Integer,Integer>();
        imageIds.put(1000,R.drawable.clear_day);
        imageIds.put(1100,R.drawable.mostly_clear_day);
        imageIds.put(1101,R.drawable.partly_cloudy_day);
        imageIds.put(1102,R.drawable.mostly_cloudy);
        imageIds.put(1001,R.drawable.cloudy);
        imageIds.put(2000,R.drawable.fog);
        imageIds.put(2100,R.drawable.fog_light);
        imageIds.put(8000,R.drawable.tstorm);
        imageIds.put(5001,R.drawable.flurries);
        imageIds.put(5100,R.drawable.snow_light);
        imageIds.put(5000,R.drawable.snow);
        imageIds.put(5101,R.drawable.snow_heavy);
        imageIds.put(7102,R.drawable.ice_pellets_light);
        imageIds.put(7000,R.drawable.ice_pellets);
        imageIds.put(7101,R.drawable.ice_pellets_heavy);
        imageIds.put(4000,R.drawable.drizzle);
        imageIds.put(6000,R.drawable.freezing_drizzle);
        imageIds.put(6200,R.drawable.freezing_rain_light);
        imageIds.put(6001,R.drawable.freezing_rain);
        imageIds.put(6201,R.drawable.freezing_rain_heavy);
        imageIds.put(4200,R.drawable.rain_light);
        imageIds.put(4001,R.drawable.rain);
        imageIds.put(4201,R.drawable.rain_heavy);
        imageIds.put(3000,R.drawable.wind_light);
        imageIds.put(3001,R.drawable.wind);
        imageIds.put(3002,R.drawable.wind_strong);


        statusIds = new HashMap<Integer,Integer>();
        statusIds.put(1000,R.string.clear				);
        statusIds.put(1100,R.string.mostly_clear	    );
        statusIds.put(1101,R.string.partly_cloudy	    );
        statusIds.put(1102,R.string.mostly_cloudy	    );
        statusIds.put(1001,R.string.cloudy			    );
        statusIds.put(2000,R.string.fog				);
        statusIds.put(2100,R.string.light_fog		    );
        statusIds.put(8000,R.string.thunderstorm	    );
        statusIds.put(5001,R.string.flurries			);
        statusIds.put(5100,R.string.light_snow		    );
        statusIds.put(5000,R.string.snow				);
        statusIds.put(5101,R.string.heavy_snow		    );
        statusIds.put(7102,R.string.light_ice_pellets	);
        statusIds.put(7000,R.string.ice_pellets		);
        statusIds.put(7101,R.string.heavy_ice_pellets	);
        statusIds.put(4000,R.string.drizzle			);
        statusIds.put(6000,R.string.freezing_drizzle	);
        statusIds.put(6200,R.string.light_freezing_rain);
        statusIds.put(6001,R.string.freezing_rain		);
        statusIds.put(6201,R.string.heavy_freezing_rain);
        statusIds.put(4200,R.string.light_rain		    );
        statusIds.put(4001,R.string.rain				);
        statusIds.put(4201,R.string.heavy_rain		    );
        statusIds.put(3000,R.string.light_wind		    );
        statusIds.put(3001,R.string.wind				);
        statusIds.put(3002,R.string.strong_wind		);
    }
}
