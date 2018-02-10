package zdalyapp.mayah.weatherpodcast.ocean;

import android.content.Context;
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

import zdalyapp.mayah.R;
import zdalyapp.mayah.weatherpodcast.ocean.dummy.DummyContent;


public class MyOceanDetailRecyclerViewAdapter extends RecyclerView.Adapter<MyOceanDetailRecyclerViewAdapter.ViewHolder> {

    private final JSONArray mValues;

    public MyOceanDetailRecyclerViewAdapter(JSONArray jsonArray, Context context) {
        mValues = jsonArray;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ocean_detail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        try {
            holder.mItem = new DummyContent.OceanDetailItem(mValues.getJSONObject(position), 1);
            holder.mTempHighLow.setText(holder.mItem.mTempHighLow);
            holder.mTemp.setText(holder.mItem.mDesc + "  " + holder.mItem.mTemp);
            holder.mWindSpeedKm.setText(holder.mItem.mWindSpeedKm);
            holder.mWindDirection.setText(holder.mItem.mWindDirection);
            holder.mWindSpeedMile.setText(holder.mItem.mWindSpeedMile);
            holder.mPrecip.setText(holder.mItem.mPrecip);
            holder.mHumidity.setText(holder.mItem.mHumidity + "  " + holder.mItem.mVisibility);
            holder.mPressure.setText(holder.mItem.mPressure);
            holder.mDesc.setText(holder.mItem.mDate);
            Picasso.with(holder.mView.getContext()).load(holder.mItem.iconURL).error(R.drawable.check_small).into(holder.iconImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mValues.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView   mDesc, mTemp, mTempHighLow, mWindSpeedKm, mWindSpeedMile, mWindDirection, mPrecip, mHumidity, mVisibility, mPressure;
        ImageView iconImage;
        View mView;
        DummyContent.OceanDetailItem mItem;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDesc = (TextView) view.findViewById(R.id.textView17);
            mTemp = (TextView) view.findViewById(R.id.textView18);
            mTempHighLow = (TextView) view.findViewById(R.id.textView19);
            mWindSpeedKm = (TextView) view.findViewById(R.id.textView20);
            mWindSpeedMile = (TextView) view.findViewById(R.id.textView26);
            mWindDirection = (TextView) view.findViewById(R.id.textView21);
            mPrecip = (TextView) view.findViewById(R.id.textView22);
            mHumidity = (TextView) view.findViewById(R.id.textView23);
            mPressure = (TextView) view.findViewById(R.id.textView25);
            iconImage = (ImageView) view.findViewById(R.id.imageView8);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDesc.getText() + "'";
        }
    }
}
