package zdalyapp.mayah.views;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import zdalyapp.mayah.R;
import zdalyapp.mayah.global.Constants;

/**
 * Created by rem on 15/06/13.
 */
public class ColorListViewAdapter extends BaseAdapter implements ListAdapter {

    private String TAG = "ColorListViewAdapter";
    private Context mContext = null;
    private int mNbElements = 0;
    private JSONArray jsonArray = null;

    public ColorListViewAdapter(Context context, JSONObject jsonObject) {
        mContext = context;
        try {
            jsonArray = jsonObject.getJSONArray("configuration");
            mNbElements = jsonArray.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        if (mNbElements % 2 == 1)
            return (mNbElements + 1) / 2;
        return mNbElements / 2;
    }

    @Override
    public JSONObject getItem(int position) {

        try {
            return jsonArray.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.list_cell_graph_color, parent, false);
        }
        else {
            Log.i(TAG, "Converting view !");
        }


        int pos1 = 2 * position;
        int pos2 = 2 * position + 1;
        Integer color = 0;
        JSONObject jsonObject1 = getItem(pos1);
        View colorLayout1 = v.findViewById(R.id.colorLayout1);
        View colorLayout2 = v.findViewById(R.id.colorLayout2);
        View colorView1 = v.findViewById(R.id.colorView1);
        View colorView2 = v.findViewById(R.id.colorView2);
        View imageView1 = v.findViewById(R.id.imageView1);
        View imageView2 = v.findViewById(R.id.imageView2);
        TextView textView1 = (TextView)v.findViewById(R.id.textView1);
        TextView textView2 = (TextView)v.findViewById(R.id.textView2);
        if (jsonObject1.has("fillColors")) {
            try {
                color = Color.parseColor(jsonObject1.getString("fillColors"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
            color = Constants.GraphColorList.get(pos1);
        colorView1.setBackgroundColor(color);

        try {
            String title = jsonObject1.getString("title");
            textView1.setText(title);
            if (title.equals("Total"))
            {
                colorView1.setVisibility(View.GONE);
                imageView1.setVisibility(View.VISIBLE);
            }
            else
            {
                colorView1.setVisibility(View.VISIBLE);
                imageView1.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (pos2 >= mNbElements)
        {
            colorLayout2.setVisibility(View.GONE);
        }
        else
        {
            JSONObject jsonObject2 = new JSONObject();
            try {
                jsonObject2 = jsonArray.getJSONObject(pos2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jsonObject2.has("fillColors")) {
                try {
                    color = Color.parseColor(jsonObject2.getString("fillColors"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
                color = Constants.GraphColorList.get(pos2);
            colorView2.setBackgroundColor(color);
            try {
                String title = jsonObject2.getString("title");
                textView2.setText(jsonObject2.getString("title"));
                if (title.equals("Total"))
                {
                    colorView2.setVisibility(View.GONE);
                    imageView2.setVisibility(View.VISIBLE);
                }
                else
                {
                    colorView2.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return v;
    }
}
