package zdalyapp.mayah.weatherpodcast.city;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zdalyapp.mayah.R;
import zdalyapp.mayah.global.Utils;

public class DetailWeatherActivity extends AppCompatActivity {

    TextView mTitleView, mDateView, mTempView, mWindSpeed, mPressure, mHumidity, mVisibility, mDesc;
    ImageView imageView;
    JSONObject jsonData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_weather);
        initView();
    }

    private void initView() {
        mTitleView = (TextView) findViewById(R.id.textView15);
        mDateView = (TextView) findViewById(R.id.textView16);
        mDesc = (TextView) findViewById(R.id.textView17);
        mTempView = (TextView) findViewById(R.id.textView18);
        mWindSpeed = (TextView) findViewById(R.id.textView19);
        mPressure = (TextView) findViewById(R.id.textView20);
        mHumidity = (TextView) findViewById(R.id.textView21);
        mVisibility = (TextView) findViewById(R.id.textView22);
        imageView = (ImageView) findViewById(R.id.imageView8);
        String strJson = Utils.GetStringFromPreference("city_detail", this);
        try {
            jsonData = new JSONObject(strJson);
            JSONObject dataObj = jsonData.getJSONObject("data");
            JSONArray conditionArr = dataObj.getJSONArray("current_condition");
            JSONArray requestArr = dataObj.getJSONArray("request");
            JSONObject jsonObject = conditionArr.getJSONObject(0);
            JSONObject requestObj = requestArr.getJSONObject(0);
            if (requestObj.has("query"))
                mTitleView.setText(requestObj.getString("query"));
            if (jsonObject.has("temp_C"))
                mTempView.setText(jsonObject.getString("temp_C"));
            if (jsonObject.has("windspeedKmph"))
                mWindSpeed.setText("Wind Speed: "+ jsonObject.getString("windspeedKmph") + "Km/h");
            if (jsonObject.has("windspeedKmph"))
                mHumidity.setText("Humidity: " + jsonObject.getString("humidity") + "%");
            if (jsonObject.has("pressure"))
                mHumidity.setText("Pressure: " + jsonObject.getString("pressure") + "mb");
            if (jsonObject.has("weatherIconUrl")) {
                JSONArray jsonArray = jsonObject.getJSONArray("weatherIconUrl");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                if (jsonObject1.has("value")) {
                    Picasso.with(this).load(jsonObject1.getString("value")).error(R.drawable.check_small).into(imageView);
                }
            }
            if (jsonObject.has("weatherDesc")) {
                JSONArray jsonArray = jsonObject.getJSONArray("weatherDesc");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                if (jsonObject1.has("value"))
                    mDesc.setText(jsonObject1.getString("value"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
