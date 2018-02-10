package zdalyapp.mayah.weatherpodcast.city;

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

import java.util.List;

import zdalyapp.mayah.R;
import zdalyapp.mayah.global.Utils;
import zdalyapp.mayah.weatherpodcast.city.CityFragment.OnCityListFragmentInteractionListener;
import zdalyapp.mayah.weatherpodcast.city.dummy.DummyContent.CityItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link CityItem} and makes a call to the
 * specified {@link OnCityListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCityDetailRecyclerViewAdapter extends RecyclerView.Adapter<MyCityDetailRecyclerViewAdapter.ViewHolder> {

    private final JSONArray mValues;
    private final Context mListener;

    public MyCityDetailRecyclerViewAdapter(JSONArray jsonArray, Context listener) {
        mValues = jsonArray;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_city_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {
            JSONObject weatherWeekObj = mValues.getJSONObject(position);
            holder.mDate.setText(weatherWeekObj.getString("date"));
            holder.mTempHighLow.setText("High: " + weatherWeekObj.getString("maxtempC") + "°C   Low: " +  weatherWeekObj.getString("mintempC"));
            JSONArray hourlyArr = weatherWeekObj.getJSONArray("hourly");
            JSONObject hourlyObj = hourlyArr.getJSONObject(0);
            JSONArray weatherIconArr = hourlyObj.getJSONArray("weatherIconUrl");
            JSONObject weatherIconObj = weatherIconArr.getJSONObject(0);
            String url = weatherIconObj.getString("value");
            JSONArray weatherDescArr = hourlyObj.getJSONArray("weatherDesc");
            JSONObject weatherDescObj = weatherDescArr.getJSONObject(0);
            String desc = weatherDescObj.getString("value");

            holder.mDesc.setText(desc);
            Picasso.with(holder.mView.getContext()).load(url).into(holder.mIcon);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);


    }

    @Override
    public int getItemCount() {
        return mValues.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public  View mView;
        public  TextView mDate;
        public  TextView mDesc;
        public  TextView mTempHighLow;
        public ImageView mIcon;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDate = (TextView) view.findViewById(R.id.textView28);
            mDesc = (TextView) view.findViewById(R.id.textView29);
            mTempHighLow = (TextView) view.findViewById(R.id.textView30);

            mIcon = (ImageView) view.findViewById(R.id.imageView10);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDate.getText() + "'";
        }
    }
}
