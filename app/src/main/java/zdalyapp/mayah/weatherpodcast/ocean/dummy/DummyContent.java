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
                Log.d("hourlyObj", hourlyObj.toString());
                int temp = Integer.parseInt(hourlyObj.getString("tempC").replaceAll("[^0-9\\\\.]+",""));
                int windspeedKmph = Integer.parseInt(hourlyObj.getString("windspeedKmph").replaceAll("[^0-9\\\\.]+",""));
                Log.d("oceandetail", windspeedKmph + " windspeedKmph");
                int humidity = Integer.parseInt(hourlyObj.getString("humidity").replaceAll("[^0-9\\\\.]+",""));
                Log.d("oceandetail", humidity + " humidity");
                int visibility = Integer.parseInt(hourlyObj.getString("visibility").replaceAll("[^0-9\\\\.]+",""));
                Log.d("oceandetail", visibility + " visibility");
                mTempHighLow = (String.format("Low: %d°C     High: %d°C", mintemp, maxtemp));
                Log.d("oceandetail", mTempHighLow);
                mTemp = (String.format("%d°C     %s°F", temp, hourlyObj.getString("tempF")));
                Log.d("oceandetail", mTemp);
                mWindSpeedKm = (String.format("Wind Speed: %dkm/h", windspeedKmph));
                Log.d("oceandetail", "mWindSpeedKm " + mWindSpeedKm);
                mWindDirection = (hourlyObj.getString("winddir16Point"));
                Log.d("oceandetail", "mWindDirection " +mWindDirection);
                mWindSpeedMile = (String.format("Wind Speed: %smiles/hour", hourlyObj.getString("windspeedMiles")));
                Log.d("oceandetail", "mWindSpeedMile " +mWindSpeedMile);
                mPrecip = (String.format("Precipitation: %smm", hourlyObj.getString("precipMM")));
                Log.d("oceandetail", "mPrecip " + mPrecip);
                mHumidity = (String.format("Humidity: %d%s", humidity, "%"));
                mVisibility = (String.format("Visibility: %dkm", visibility));
                mPressure = (String.format("Pressure: %smb", hourlyObj.getString("pressure")));
                JSONArray iconArr = hourlyObj.getJSONArray("weatherIconUrl");
                JSONObject iconObj = iconArr.getJSONObject(0);
                iconURL = iconObj.getString("value");
                JSONArray weatherDescArr = hourlyObj.getJSONArray("weatherDesc");
                JSONObject weatherDescObj = weatherDescArr.getJSONObject(0);
                Log.d("weatherDescObj", weatherDescObj.toString());
                String weatherDesc = weatherDescObj.getString("value");
                mDesc = (weatherDesc);
                mDate = (weatherObj.getString("date"));
            } catch (JSONException e) {

                e.printStackTrace();
            }

        }
        @Override
        public String toString() {
            return title;
        }
    }
}
