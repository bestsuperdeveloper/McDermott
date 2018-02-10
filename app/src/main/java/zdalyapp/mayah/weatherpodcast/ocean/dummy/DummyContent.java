package zdalyapp.mayah.weatherpodcast.ocean.dummy;

import android.util.Log;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import zdalyapp.mayah.R;

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

        public OceanItem(JSONObject jsonData)
        {
            try {
                this.title = jsonData.getString("name");
                double lat = Double.parseDouble(jsonData.getString("lat").replaceAll("[^0-9\\\\.]+",""));
                double lon = Double.parseDouble(jsonData.getString("lon").replaceAll("[^0-9\\\\.]+",""));
                this.latitude = String.format("%.2f", lat);
                this.longitude = String.format("%.2f", lon);
            } catch (JSONException e) {


            }

        }
        @Override
        public String toString() {
            return title;
        }
    }
    public static class OceanDetailItem implements Serializable{
        public  String title = "";
        public  String location = "";
        public  String mTempHighLow = "";
        public  String mTemp = "";
        public  String mWindSpeedKm = "";
        public  String mWindDirection = "";
        public  String mWindSpeedMile = "";
        public  String mPrecip = "";
        public  String mHumidity = "";
        public  String mVisibility = "";
        public  String mPressure = "";
        public  String mDesc = "";
        public  String mDate = "";
        public  String iconURL = "";
        public OceanDetailItem(JSONObject dataJson)
        {
            try {
                title = dataJson.getString("name");

                double lat = Double.parseDouble(dataJson.getString("lat").replaceAll("[^0-9\\\\.]+",""));
                double lon = Double.parseDouble(dataJson.getString("lon").replaceAll("[^0-9\\\\.]+",""));
                this.location = (String.format("Latitude: %.2f, Longitude: %.2f", lat, lon));
                JSONObject marineObj = dataJson.getJSONObject("weather");
                JSONObject dataObj = marineObj.getJSONObject("data");
                JSONArray weatherArr = dataObj.getJSONArray("weather");
                JSONObject weatherObj = weatherArr.getJSONObject(0);
                int maxtemp = Integer.parseInt(weatherObj.getString("maxtempC").replaceAll("[^0-9\\\\.]+",""));
                int mintemp = Integer.parseInt(weatherObj.getString("mintempC").replaceAll("[^0-9\\\\.]+",""));
                JSONArray hourlyArr = weatherObj.getJSONArray("hourly");
                JSONObject hourlyObj = hourlyArr.getJSONObject(0);
                int temp = Integer.parseInt(hourlyObj.getString("tempC").replaceAll("[^0-9\\\\.]+",""));
                int windspeedKmph = Integer.parseInt(hourlyObj.getString("windspeedKmph").replaceAll("[^0-9\\\\.]+",""));
                int humidity = Integer.parseInt(hourlyObj.getString("humidity").replaceAll("[^0-9\\\\.]+",""));
                int visibility = Integer.parseInt(hourlyObj.getString("visibility").replaceAll("[^0-9\\\\.]+",""));
                mTempHighLow = (String.format("Low: %d°C     High: %d°C", mintemp, maxtemp));
                Log.d("oceandetail", mTempHighLow);
                mTemp = (String.format("%d°C     %s°F", temp, hourlyObj.getString("tempF")));
                Log.d("oceandetail", mTemp);
                mWindSpeedKm.valueOf(String.format("Wind Speed: %dkm/h", windspeedKmph));
                Log.d("oceandetail", mWindSpeedKm);
                mWindDirection.valueOf(hourlyObj.getString("winddir16Point"));
                Log.d("oceandetail", mWindDirection);
                mWindSpeedMile.valueOf(String.format("Wind Speed: %smiles/hour", hourlyObj.getString("windspeedMiles")));
                mPrecip.valueOf(String.format("Precipitation: %smm", hourlyObj.getString("precipMM")));
                mHumidity.valueOf(String.format("Humidity: %d%s", humidity, "%"));
                mVisibility.valueOf(String.format("Visibility: %dkm", visibility));
                mPressure.valueOf(String.format("Pressure: %smb", hourlyObj.getString("pressure")));
                JSONArray iconArr = hourlyObj.getJSONArray("weatherIconUrl");
                JSONObject iconObj = iconArr.getJSONObject(0);
                iconURL = iconObj.getString("value");
                JSONArray weatherDescArr = hourlyObj.getJSONArray("weatherDesc");
                JSONObject weatherDescObj = weatherDescArr.getJSONObject(0);
                String weatherDesc = weatherDescObj.getString("value");
                mDesc.valueOf(weatherDesc);
                mDate.valueOf(weatherDescObj.getString("date"));
            } catch (JSONException e) {


            }

        }
        @Override
        public String toString() {
            return title;
        }
    }
}
