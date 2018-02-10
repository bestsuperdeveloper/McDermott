package zdalyapp.mayah.weatherpodcast.ocean.dummy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public static class OceanItem {
        public  String title = "";
        public  String latitude = "";
        public  String longitude = "";
        public  String dataJson = "";



        public OceanItem(JSONObject jsonData)
        {
            try {
                this.title = jsonData.getString("name");
                double lat = Double.parseDouble(jsonData.getString("lat").replaceAll("[^0-9\\\\.]+",""));
                double lon = Double.parseDouble(jsonData.getString("lon").replaceAll("[^0-9\\\\.]+",""));
                this.latitude = String.format("%.2f", lat);
                this.longitude = String.format("%.2f", lon);
                dataJson = jsonData.toString();
            } catch (JSONException e) {


            }

        }
        @Override
        public String toString() {
            return title;
        }
    }
}
