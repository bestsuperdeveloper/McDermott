package zdalyapp.mayah.weatherpodcast.city.dummy;

import org.json.JSONArray;
import org.json.JSONException;
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
        public  String title = "";
        public  String tempc = "";
        public  String tempf = "";
        public  String windspeed = "";
        public  String humidity = "";
        public  String desc = "";
        public  String imgURL = "";

        public CityItem(String id, String content, String details) {
            this.tempc = id;
            this.tempf = content;
            this.windspeed = details;
        }

        public CityItem(JSONObject jsonData)
        {

            try {


                JSONObject dataObj = jsonData.getJSONObject("data");
                JSONArray conditionArr = dataObj.getJSONArray("current_condition");
                JSONArray requestArr = dataObj.getJSONArray("request");
                JSONObject jsonObject = conditionArr.getJSONObject(0);
                JSONObject requestObj = requestArr.getJSONObject(0);
                if (requestObj.has("query"))
                    this.title = requestObj.getString("query");
                if (jsonObject.has("temp_C"))
                    this.tempc = jsonObject.getString("temp_C");
                if (jsonObject.has("temp_C"))
                    this.tempc = jsonObject.getString("temp_C");
                if (jsonObject.has("temp_C"))
                    this.tempf = jsonObject.getString("temp_F");
                if (jsonObject.has("windspeedKmph"))
                    this.windspeed = jsonObject.getString("windspeedKmph");
                if (jsonObject.has("windspeedKmph"))
                    this.humidity = jsonObject.getString("humidity");
                if (jsonObject.has("weatherIconUrl")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("weatherIconUrl");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    if (jsonObject1.has("value"))
                        this.imgURL = jsonObject1.getString("value");
                }
                if (jsonObject.has("weatherDesc")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("weatherDesc");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    if (jsonObject1.has("value"))
                        this.desc = jsonObject1.getString("value");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        public String toString() {
            return tempc;
        }
    }
}
