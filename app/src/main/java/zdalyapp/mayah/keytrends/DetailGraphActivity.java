package zdalyapp.mayah.keytrends;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zdalyapp.mayah.R;
import zdalyapp.mayah.global.Constants;
import zdalyapp.mayah.views.ColorListViewAdapter;
import zdalyapp.mayah.views.HorizontalListView;
import zdalyapp.mayah.views.MCGraphView;

public class DetailGraphActivity extends AppCompatActivity {

    JSONObject jsonObject;
    private TextView mTitleView;
    private HorizontalListView mColorListView;
    private MCGraphView mGraphView;

    String mTitle = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_graph);
        initView();

        {
            String dataString = Constants.dataString;
            Log.d("dataString", dataString);
            try {
                jsonObject = new JSONObject(dataString);
                mTitle = jsonObject.getString("title");
                DrawGraph();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    private void initView() {
        mTitleView = (TextView) findViewById(R.id.textView8);

        mColorListView = (HorizontalListView) findViewById(R.id.colorlistview);
        mGraphView = (MCGraphView) findViewById(R.id.graphView);
        findViewById(R.id.imageButton1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // finish();
            }
        });
        findViewById(R.id.imageButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, mTitle);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

    }

    private void DrawGraph() {
        mColorListView.setAdapter(new ColorListViewAdapter(this, jsonObject));
        Log.d("jsonObject", jsonObject.toString());
        mGraphView.setExampleString(jsonObject.toString());

        //      holder.mGraphView.invalidate();
        try {
            mTitleView.setText(jsonObject.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
            mTitleView.setText("");
        }

    }
}
