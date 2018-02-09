package zdalyapp.mayah.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import zdalyapp.mayah.R;
import zdalyapp.mayah.dailynews.DailyNewsFragment;
import zdalyapp.mayah.dailynews.dummy.DummyContent;
import zdalyapp.mayah.global.Constants;
import zdalyapp.mayah.global.WebViewFragment;
import zdalyapp.mayah.keytrends.DetailGraphActivity;
import zdalyapp.mayah.keytrends.industry.IndustryFragment;
import zdalyapp.mayah.keytrends.KeyTrendsFragment;
import zdalyapp.mayah.keytrends.production.ProductionFragment;

public class DashboardActivity extends AppCompatActivity implements DailyNewsFragment.OnListDailyNewsInteractionListener, WebViewFragment.OnWebFragmentInteractionListener
    , KeyTrendsFragment.OnTrendsInteractionListener,   IndustryFragment.OnIndustryListFragmentInteractionListener, ProductionFragment.OnProductionListFragmentInteractionListener
{

    Button button1, button2, button3, button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ShowView();
    }

    private void ShowView() {
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

    private void ShowFragment(int fragValue)
    {
        Fragment fr;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (fragValue)
        {
            case 0:
            {
                fr = DailyNewsFragment.newInstance(1);
                transaction.replace(R.id.mainFrame, fr);
                transaction.commit();
                break;
            }
            case 1:
            {
                fr = KeyTrendsFragment.newInstance("", "");
                transaction.replace(R.id.mainFrame, fr);
                transaction.commit();
            }
        }
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

    @Override
    public void onProductionListFragmentInteraction(JSONObject item) {

        Intent intent = new Intent(this, DetailGraphActivity.class);
        Constants.dataString = item.toString();

        startActivity(intent);

    }
}
