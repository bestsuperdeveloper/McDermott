package zdalyapp.mayah.dashboard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zdalyapp.mayah.McDomatsApp;
import zdalyapp.mayah.R;
import zdalyapp.mayah.dailynews.DailyNewsFragment;
import zdalyapp.mayah.dailynews.dummy.DummyContent;
import zdalyapp.mayah.global.Constants;
import zdalyapp.mayah.global.Utils;
import zdalyapp.mayah.global.WebViewFragment;
import zdalyapp.mayah.keytrends.DetailGraphActivity;
import zdalyapp.mayah.keytrends.industry.IndustryFragment;
import zdalyapp.mayah.keytrends.KeyTrendsFragment;
import zdalyapp.mayah.keytrends.production.ProductionFragment;
import zdalyapp.mayah.spotprices.SpotPriceFragment;
import zdalyapp.mayah.weatherpodcast.WeatherForecastFragment;
import zdalyapp.mayah.weatherpodcast.city.CityFragment;
import zdalyapp.mayah.weatherpodcast.city.DetailWeatherActivity;
import zdalyapp.mayah.weatherpodcast.ocean.OceanDetailActivity;
import zdalyapp.mayah.weatherpodcast.ocean.OceanFragment;

public class DashboardActivity extends AppCompatActivity implements DailyNewsFragment.OnListDailyNewsInteractionListener, WebViewFragment.OnWebFragmentInteractionListener
    , KeyTrendsFragment.OnTrendsInteractionListener,   IndustryFragment.OnIndustryListFragmentInteractionListener, ProductionFragment.OnProductionListFragmentInteractionListener
    , SpotPriceFragment.OnSpotListFragmentInteractionListener, WeatherForecastFragment.OnWeahterFragmentInteractionListener
    , OceanFragment.OnOceanFragmentInteractionListener, CityFragment.OnCityListFragmentInteractionListener
{

    Button button1, button2, button3, button4;
    ViewPager viewPager;
    TabLayout tabLayout;

    LinearLayout tabLinearLayout;
    private Dialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ShowView();
    }

    private void ShowView() {

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLinearLayout = (LinearLayout) findViewById(R.id.tabsLayout);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        button4.setTransformationMethod(null);
        button1.setTransformationMethod(null);
        button3.setTransformationMethod(null);
        button2.setTransformationMethod(null);

        button1.setBackgroundColor(getResources().getColor(R.color.colorButton2));
        button2.setBackgroundColor(getResources().getColor(R.color.colorButton1));
        button3.setBackgroundColor(getResources().getColor(R.color.colorButton1));
        button4.setBackgroundColor(getResources().getColor(R.color.colorButton1));
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button1.setBackgroundColor(getResources().getColor(R.color.colorButton2));
                button2.setBackgroundColor(getResources().getColor(R.color.colorButton1));
                button3.setBackgroundColor(getResources().getColor(R.color.colorButton1));
                button4.setBackgroundColor(getResources().getColor(R.color.colorButton1));
                ShowFragment(0);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button2.setBackgroundColor(getResources().getColor(R.color.colorButton2));
                button1.setBackgroundColor(getResources().getColor(R.color.colorButton1));
                button3.setBackgroundColor(getResources().getColor(R.color.colorButton1));
                button4.setBackgroundColor(getResources().getColor(R.color.colorButton1));
                ShowFragment(1);

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button3.setBackgroundColor(getResources().getColor(R.color.colorButton2));
                button1.setBackgroundColor(getResources().getColor(R.color.colorButton1));
                button2.setBackgroundColor(getResources().getColor(R.color.colorButton1));
                button4.setBackgroundColor(getResources().getColor(R.color.colorButton1));
                ShowFragment(2);

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button4.setBackgroundColor(getResources().getColor(R.color.colorButton2));
                button1.setBackgroundColor(getResources().getColor(R.color.colorButton1));
                button2.setBackgroundColor(getResources().getColor(R.color.colorButton1));
                button3.setBackgroundColor(getResources().getColor(R.color.colorButton1));
                ShowFragment(3);

            }
        });

        ShowFragment(0);
    }

    private void clearCurrentFragments()
    {
        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.mainFrame)).commit();
    }

    private void triggerView(boolean flag)
    {
        if (flag == false)
        {
            findViewById(R.id.mainFrame).setVisibility(View.GONE);
            tabLinearLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            findViewById(R.id.mainFrame).setVisibility(View.VISIBLE);
            tabLinearLayout.setVisibility(View.GONE);
        }
    }

    private void ShowFragment(int fragValue)
    {
        Fragment fr;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (fragValue)
        {
            case 0:
            {
                triggerView(true);
                fr = (DailyNewsFragment) fm.findFragmentByTag("news");
                if (fr == null)
                {
                    fr = DailyNewsFragment.newInstance(1);
                    transaction.replace(R.id.mainFrame, fr, "news").commit();
                }
                break;
            }
            case 1:
            {

                fr = (KeyTrendsFragment) fm.findFragmentByTag("trend");
                if (fr == null)
                {
                    fr = KeyTrendsFragment.newInstance("", "");
                    transaction.replace(R.id.mainFrame, fr, "trend").commit();
                }
/*
                GetTrendData();*/

                break;
            }
            case 2:
            {
                triggerView(true);
                fr = (SpotPriceFragment) fm.findFragmentByTag("spot");
                if (fr == null)
                {
                    fr = SpotPriceFragment.newInstance(1);
                    transaction.replace(R.id.mainFrame, fr, "spot").commit();
                }

                break;
            }
            case 3:
            {


                fr = (WeatherForecastFragment) fm.findFragmentByTag("weather");
                if (fr == null)
                {
                    fr = WeatherForecastFragment.newInstance("", "");
                    transaction.replace(R.id.mainFrame, fr, "weather").commit();
                }



              /*  GetWeatherForecastData();*/
                break;
            }
        }

    }
    private void GetTrendData() {
        String id = Utils.GetStringFromPreference(Constants.USERID, this);
        String url = Constants.API_URL + Constants.KEY_TRENDS_URL + "?id=" + id;
        showLoadingDialog("Getting trends...");
        Log.d("URL", url);
        JsonArrayRequest newsRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("Response", response.toString());

                        ParseDataFromTrendJsonArray(response);
                        hideDialog();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                    }
                }
        );
        McDomatsApp.getInstance().addToRequestQueue(newsRequest, "trensRequest");
    }

    private void showLoadingDialog(String msg) {
        if (loginDialog == null) {
            loginDialog = new Dialog(this);
            loginDialog.setContentView(R.layout.activity_login_view);
            ImageView imageView = loginDialog.findViewById(R.id.imageView);

        }
        TextView textView = loginDialog.findViewById(R.id.textView);
        textView.setText(msg);
        loginDialog.show();
    }

    private void hideDialog() {
        if (loginDialog != null)
            loginDialog.dismiss();
    }
    private void ParseDataWeatherFromJsonArray(JSONObject response) {
        triggerView(false);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        JSONArray marineArr = new JSONArray();
        JSONArray weatherArr = new JSONArray();
        try {
            marineArr = response.getJSONArray("marine");
            weatherArr = response.getJSONArray("weather");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(CityFragment.newInstance(1, weatherArr.toString()), "city");
        adapter.addFragment(OceanFragment.newInstance(marineArr.toString(), ""), "ocean");

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager, true);


    }
    private void GetWeatherForecastData() {
        String id = Utils.GetStringFromPreference(Constants.USERID, this);
        String url = Constants.API_URL + Constants.WEATHER_FORECAST_URL + "?id=" + id;
        showLoadingDialog("Getting weather list...");
        Log.d("URL", url);
        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        hideDialog();
                        ParseDataWeatherFromJsonArray(response);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getLocalizedMessage());
                        hideDialog();

                    }
                }
        );
// Add the request to the RequestQueue.
        McDomatsApp.getInstance().addToRequestQueue(loginRequest, "spotRequest");

    }
    private void ParseDataFromTrendJsonArray(JSONArray response) {
        int length = response.length();
        HashMap<String, Integer> map = new HashMap<>();
        HashMap<String, JSONArray> dataByType = new HashMap<>();
        JSONArray list;
        ArrayList<String> headerTitleList = new ArrayList<>();

        int maxColorCount = 1;
        for (int i = 0; i < length; i++)
        {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                String type = jsonObject.getString("type");
                JSONArray configArray = jsonObject.getJSONArray("configuration");

                if (maxColorCount < configArray.length())
                    maxColorCount = configArray.length();

                if (type != null && !type.equals("") && !map.containsKey(type))
                {
                    map.put(type, 1);
                    headerTitleList.add(type);
                    Log.d("type", type);
                    dataByType.put(type, new JSONArray());
                }

                if (!dataByType.containsKey(type))
                {
                    list = new JSONArray(); list.put(jsonObject);
                    dataByType.put(type, list);
                }
                else
                {
                    dataByType.get(type).put(jsonObject);
                }

            } catch (JSONException e) {

            }
        }
        if (maxColorCount > Constants.GraphColorList.size())
            Utils.makeGraphColor(maxColorCount - Constants.GraphColorList.size());
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        int size = headerTitleList.size();

        for (int i = 0; i < size; i++)
        {
            String type = headerTitleList.get(i);

            adapter.addFragment(ProductionFragment.newInstance(1, dataByType.get(type).toString()), type);
        }

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        triggerView(false);

    }
    @Override
    public void onListFragmentInteraction(DummyContent.DailyNewsItem item) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.mainFrame, WebViewFragment.newInstance(item.url, "")).addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onListShareInteraction(DummyContent.DailyNewsItem item) {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, item.id + " " + item.url);
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Fragment fr;
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
    }

    @Override
    public void onTrendInteraction(Uri uri) {

    }

    @Override
    public void onIndustryListFragmentInteraction(zdalyapp.mayah.keytrends.industry.dummy.DummyContent.IndustryItem item) {

    }
    private String stringField;
    private int intField;
    private List<Object> arrayField;

    @Override
    public void onWeatherFragmentInteraction(Uri uri) {

    }

    @Override
    public void onOceanFragmentInteraction(zdalyapp.mayah.weatherpodcast.ocean.dummy.DummyContent.OceanDetailItem uri) {

        Intent intent = new Intent(this, OceanDetailActivity.class);
        intent.putExtra("data", uri);
        startActivity(intent);
    }

    @Override
    public void onCityListFragmentInteraction(zdalyapp.mayah.weatherpodcast.city.dummy.DummyContent.CityItem item) {

        Toast.makeText(this, item.title, Toast.LENGTH_LONG).show();
    }

    private enum DataHolder {
        INSTANCE;

        private List<Object> mObjectList;

        public static boolean hasData() {
            return INSTANCE.mObjectList != null;
        }

        public static void setData(final List<Object> objectList) {
            INSTANCE.mObjectList = objectList;
        }

        public static List<Object> getData() {
            final List<Object> retList = INSTANCE.mObjectList;
            INSTANCE.mObjectList = null;
            return retList;
        }
    }
    private final static String ARG_STRING = "ARG_STRING";
    private final static String ARG_INT = "ARG_INT";
    @Override
    public void onProductionListFragmentInteraction(JSONObject item) {


        Intent intent = new Intent(this, DetailGraphActivity.class);


        McDomatsApp.getInstance().MainAct = item.toString();
        Log.d("itemSize", item.toString().length() + "");
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

        //DetailGraphActivity.startActivity( DashboardActivity.this, item.toString(),
//        0, new ArrayList<>());

    }

    @Override
    public void onSpotListFragmentInteraction(zdalyapp.mayah.spotprices.dummy.DummyContent.SpotPriceItem item) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
