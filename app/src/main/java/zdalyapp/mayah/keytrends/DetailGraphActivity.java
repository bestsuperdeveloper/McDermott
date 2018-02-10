package zdalyapp.mayah.keytrends;

import android.content.Context;
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

import java.util.List;

import zdalyapp.mayah.McDomatsApp;
import zdalyapp.mayah.R;
import zdalyapp.mayah.global.Constants;
import zdalyapp.mayah.global.Utils;
import zdalyapp.mayah.views.ColorListViewAdapter;
import zdalyapp.mayah.views.HorizontalListView;
import zdalyapp.mayah.views.MCGraphView;

public class DetailGraphActivity extends AppCompatActivity {

    public static JSONObject jsonObject;
    private TextView mTitleView;
    private HorizontalListView mColorListView;
    private MCGraphView mGraphView;
    private final static String ARG_STRING = "ARG_STRING";
    private final static String ARG_INT = "ARG_INT";

    private String stringField;
    private int intField;
    private List<Object> arrayField;

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
    String mTitle = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_graph);

        final Intent intent = getIntent();

        // And retrieve arguments if there are any
        if (intent.hasExtra(ARG_STRING)) {
            stringField = intent.getExtras().getString(ARG_STRING);
        }
        if (intent.hasExtra(ARG_INT)) {
            intField = intent.getExtras().getInt(ARG_INT);
        }
        // And we retrieve large data from enum
        if (DataHolder.hasData()) {
            arrayField = DataHolder.getData();
        }
        initView();

        {



            try {
                jsonObject = new JSONObject( McDomatsApp.getInstance().MainAct);
                mTitle = jsonObject.getString("title");
                DrawGraph();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
    @Override
    protected void onDestroy() {
        //android.os.Process.killProcess(android.os.Process.myPid());

        super.onDestroy();
        if(jsonObject!=null)
        {
            McDomatsApp.getInstance().setMainAct(null);
            jsonObject = null;
            Runtime.getRuntime().gc();
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

        mGraphView.setExampleString(jsonObject.toString());

        //      holder.mGraphView.invalidate();
        try {
            mTitleView.setText(jsonObject.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
            mTitleView.setText("");
        }

    }


    public static void startActivity(final Context context, final String stringArg,
                                     final int intArg, final List<Object> objectList) {

        // Initialize a new intent
        final Intent intent = new Intent(context, DetailGraphActivity.class);

        // To speed things up :)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        // And add arguments to the Intent
        intent.putExtra(ARG_STRING, stringArg);
        intent.putExtra(ARG_INT, intArg);

        // Now we put the large data into our enum instead of using Intent extras
        DataHolder.setData(objectList);

        context.startActivity(intent);
    }
}
