package zdalyapp.mayah.weatherpodcast.city.dummy;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */





    /**
     * A dummy item representing a piece of content.
     */
    public static class CityItem {
        public  String tempc;
        public  String tempf;
        public  String windspeed;
        public  String humidity;
        public  String midst;
        public  String imgURL;

        public CityItem(String id, String content, String details) {
            this.tempc = id;
            this.tempf = content;
            this.windspeed = details;
        }

        public CityItem(JSONObject jsonObject)
        {

        }
        @Override
        public String toString() {
            return tempc;
        }
    }
}
