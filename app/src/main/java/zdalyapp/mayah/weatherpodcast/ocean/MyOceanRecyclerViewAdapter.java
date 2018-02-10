package zdalyapp.mayah.weatherpodcast.ocean;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import zdalyapp.mayah.R;
import zdalyapp.mayah.global.Utils;
import zdalyapp.mayah.weatherpodcast.ocean.dummy.DummyContent;



public class MyOceanRecyclerViewAdapter extends RecyclerView.Adapter<MyOceanRecyclerViewAdapter.ViewHolder> {

    private final JSONArray mValues;
    private final OceanFragment.OnOceanFragmentInteractionListener mListener;

    public MyOceanRecyclerViewAdapter(JSONArray jsonArray, OceanFragment.OnOceanFragmentInteractionListener listener) {
        mValues = jsonArray;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ocean_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            holder.mItem = mValues.getJSONObject(position);
            DummyContent.OceanItem oceanItem = new DummyContent.OceanItem(holder.mItem);
            holder.mTitle.setText(oceanItem.title);
            holder.mLatitude.setText("Latitude:  " + oceanItem.latitude);
            holder.mLongitude.setText("Longitude:  " + oceanItem.longitude);
            final DummyContent.OceanDetailItem detailItem = new DummyContent.OceanDetailItem(holder.mItem);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        JSONObject marineObj = null;
                        try {
                            marineObj = holder.mItem.getJSONObject("weather");
                            JSONObject dataObj = marineObj.getJSONObject("data");
                            JSONArray weatherArr = dataObj.getJSONArray("weather");
                            Utils.SetStringFromPreference("weather_detail", weatherArr.toString(), holder.mView.getContext());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onOceanFragmentInteraction(detailItem);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return mValues.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public  View mView;
        public  TextView mTitle;
        public  TextView mLatitude;
        public  TextView mLongitude;

        public JSONObject mItem;

        public ViewHolder(View view) {
            super(view);
            mView =  view.findViewById(R.id.mainLayout);
            mTitle = (TextView) view.findViewById(R.id.textView9);
            mLatitude = (TextView) view.findViewById(R.id.textView10);
            mLongitude = (TextView) view.findViewById(R.id.textView11);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }
}
