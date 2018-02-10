package zdalyapp.mayah.weatherpodcast.ocean;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zdalyapp.mayah.R;
import zdalyapp.mayah.global.Utils;
import zdalyapp.mayah.weatherpodcast.ocean.dummy.DummyContent;

public class OceanDetailActivity extends AppCompatActivity {

    DummyContent.OceanDetailItem detailItem;
    JSONArray weatherArr;
    TextView mTitleView, mLocation, mDate, mDesc, mTemp, mTempHighLow, mWindSpeedKm, mWindSpeedMile, mWindDirection, mPrecip, mHumidity, mVisibility, mPressure;
    ImageView iconImage;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocean_detail);
        if (getIntent() != null)
        {
            detailItem = (DummyContent.OceanDetailItem) getIntent().getSerializableExtra("data");
        }
        initView();
        ShowData();
    }

    private void ShowData() {
            mTitleView.setText(detailItem.title);
            mLocation.setText(detailItem.location);
            mTempHighLow.setText(detailItem.mTempHighLow);
            mTemp.setText(detailItem.mTemp);
            mWindSpeedKm.setText(detailItem.mWindSpeedKm);
            mWindDirection.setText(detailItem.mWindDirection);
            mWindSpeedMile.setText(detailItem.mWindSpeedMile);
            mPrecip.setText(detailItem.mPrecip);
            mHumidity.setText(detailItem.mHumidity);
            mVisibility.setText(detailItem.mVisibility);
            mPressure.setText(detailItem.mPressure);
            mDesc.setText(detailItem.mDesc);
            mDate.setText(detailItem.mDate);
            Picasso.with(this).load(detailItem.iconURL).error(R.drawable.check_small).into(iconImage);
            String jsonStr = Utils.GetStringFromPreference("weather_detail", this);
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            JSONArray tempJson = new JSONArray();
            for (int i = 1; i < jsonArray.length(); i++)
            {
                tempJson.put(jsonArray.get(i));
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new MyOceanDetailRecyclerViewAdapter(tempJson, this));

        } catch (JSONException e) {

                e.printStackTrace();
        }
    }

    private void initView() {
        mTitleView = (TextView) findViewById(R.id.textView15);
        mLocation = (TextView) findViewById(R.id.textView16);
        mDate = (TextView) findViewById(R.id.textView27);
        mDesc = (TextView) findViewById(R.id.textView17);
        mTemp = (TextView) findViewById(R.id.textView18);
        mTempHighLow = (TextView) findViewById(R.id.textView19);
        mWindSpeedKm = (TextView) findViewById(R.id.textView20);
        mWindSpeedMile = (TextView) findViewById(R.id.textView26);
        mWindDirection = (TextView) findViewById(R.id.textView21);
        mPrecip = (TextView) findViewById(R.id.textView22);
        mHumidity = (TextView) findViewById(R.id.textView23);
        mVisibility = (TextView) findViewById(R.id.textView24);
        mPressure = (TextView) findViewById(R.id.textView25);
        iconImage = (ImageView) findViewById(R.id.imageView8);
        recyclerView = (RecyclerView ) findViewById(R.id.listView);
        findViewById(R.id.imageButton6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
