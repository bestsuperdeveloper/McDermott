package zdalyapp.mayah.weatherpodcast.city;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
            JSONObject conditionObj = conditionArr.getJSONObject(0);
            JSONObject requestObj = requestArr.getJSONObject(0);
            if (requestObj.has("query"))
                mTitleView.setText(requestObj.getString("query"));
            if (conditionObj.has("temp_C"))
                mTempView.setText(conditionObj.getString("temp_C") + "°C");
            if (conditionObj.has("windspeedKmph"))
                mWindSpeed.setText("Wind Speed: "+ conditionObj.getString("windspeedKmph") + "Km/h");
            if (conditionObj.has("humidity"))
                mHumidity.setText("Humidity: " + conditionObj.getString("humidity") + "%");
            if (conditionObj.has("pressure"))
                mPressure.setText("Pressure: " + conditionObj.getString("pressure") + "mb");
            if (conditionObj.has("visibility"))
                mVisibility.setText("Visibility: " + conditionObj.getString("visibility") + "km");

            if (conditionObj.has("weatherIconUrl")) {
                JSONArray jsonArray = conditionObj.getJSONArray("weatherIconUrl");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                if (jsonObject1.has("value")) {
                    Picasso.with(this).load(jsonObject1.getString("value")).error(R.drawable.check_small).into(imageView);
                }
            }
            JSONArray weatherArr = dataObj.getJSONArray("weather");
            if (conditionObj.has("weatherDesc")) {
                JSONArray jsonArray = conditionObj.getJSONArray("weatherDesc");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                if (jsonObject1.has("value"))
                    mDesc.setText(jsonObject1.getString("value"));
            }
            JSONObject weatherObj = weatherArr.getJSONObject(0);
            mDateView.setText(weatherObj.getString("date"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        findViewById(R.id.imageButton6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
