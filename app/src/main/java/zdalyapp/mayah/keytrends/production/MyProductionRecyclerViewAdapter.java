package zdalyapp.mayah.keytrends.production;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zdalyapp.mayah.R;
import zdalyapp.mayah.global.Utils;
import zdalyapp.mayah.keytrends.production.ProductionFragment.OnProductionListFragmentInteractionListener;
import zdalyapp.mayah.keytrends.production.dummy.DummyContent;
import zdalyapp.mayah.keytrends.production.dummy.DummyContent.ProductItem;
import zdalyapp.mayah.views.ColorListViewAdapter;
import zdalyapp.mayah.views.HorizontalListView;
import zdalyapp.mayah.views.MCGraphView;

import java.util.List;


public class MyProductionRecyclerViewAdapter extends RecyclerView.Adapter<MyProductionRecyclerViewAdapter.ViewHolder> {

    private final JSONArray mValues;
    private final OnProductionListFragmentInteractionListener mListener;

    public MyProductionRecyclerViewAdapter(JSONArray array, OnProductionListFragmentInteractionListener listener) {
        mValues = array;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_production, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final JSONObject jsonObject = getItem(position);

//        try {
//            holder.mIdView.setText(jsonObject.getString("title"));
//            holder.mContentView.setText(jsonObject.getString("color"));
//            holder.mColorListView.setAdapter(new ColorListViewAdapter(holder.mView.getContext(), jsonObject));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        holder.mColorListView.setAdapter(new ColorListViewAdapter(holder.mView.getContext(), jsonObject));
        ViewGroup.LayoutParams params = holder.mGraphView.getLayoutParams();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("values");
            params.width = jsonArray.length() *  Utils.pxToDp(200);
//            Log.d("graphWidth", params.width + " " + jsonArray.length() + " " + position);
        //    holder.mGraphView.setLayoutParams(params);
            holder.mGraphView.setExampleString(jsonObject.toString());
        } catch (JSONException e) {


        }


  //      holder.mGraphView.invalidate();
        try {
            holder.mTitleView.setText(jsonObject.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
            holder.mTitleView.setText("");
        }
        holder.mDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onProductionListFragmentInteraction(jsonObject);
                }
            }
        });
    }

    public JSONObject getItem(int position)
    {
        try {
            return mValues.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public int getItemCount() {
        return mValues.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public  View mView;

        public  TextView mTitleView;
        public  HorizontalListView mColorListView;
        public MCGraphView mGraphView;
        public ImageButton mDetailButton;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.textView8);

            mColorListView = (HorizontalListView) view.findViewById(R.id.colorlistview);
            mGraphView = (MCGraphView) view.findViewById(R.id.graphView);
            mDetailButton = (ImageButton) view.findViewById(R.id.imageButton5);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
