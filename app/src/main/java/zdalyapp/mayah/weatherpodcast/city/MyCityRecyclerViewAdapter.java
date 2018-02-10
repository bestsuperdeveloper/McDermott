package zdalyapp.mayah.weatherpodcast.city;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import zdalyapp.mayah.R;
import zdalyapp.mayah.weatherpodcast.city.CityFragment.OnCityListFragmentInteractionListener;
import zdalyapp.mayah.weatherpodcast.city.dummy.DummyContent.CityItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link CityItem} and makes a call to the
 * specified {@link OnCityListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCityRecyclerViewAdapter extends RecyclerView.Adapter<MyCityRecyclerViewAdapter.ViewHolder> {

    private final List<CityItem> mValues;
    private final OnCityListFragmentInteractionListener mListener;

    public MyCityRecyclerViewAdapter(List<CityItem> items, OnCityListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);

        Picasso.with(holder.mView.getContext()).load(holder.mItem.imgURL).error(R.drawable.check_small).into(holder.mIcon);
      //  Glide.with(holder.mView.getContext()).load(holder.mItem.imgURL).into(holder.mIcon);
        holder.mTitle.setText(holder.mItem.title);
        holder.mTempC.setText(String.format("Tempc:   %s°C", holder.mItem.tempc));
        holder.mTempF.setText(String.format("Tempf:   %s°F", holder.mItem.tempf));
        holder.mWindSpeed.setText(String.format("Wind Speed:   %sKm/h", holder.mItem.windspeed));
        holder.mHumidity.setText(String.format("Humidity:   %s", holder.mItem.humidity) + "%");
        holder.mDesc.setText(String.format("%s", holder.mItem.desc));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onCityListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public  View mView;
        public  TextView mTitle;
        public  TextView mTempC;
        public  TextView mTempF;
        public  TextView mWindSpeed;
        public  TextView mHumidity;
        public  TextView mDesc;
        public ImageView mIcon;
        public CityItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView =  view.findViewById(R.id.mainLayout);
            mTitle = (TextView) view.findViewById(R.id.textView9);
            mTempC = (TextView) view.findViewById(R.id.textView10);
            mTempF = (TextView) view.findViewById(R.id.textView11);
            mWindSpeed = (TextView) view.findViewById(R.id.textView12);
            mHumidity = (TextView) view.findViewById(R.id.textView13);
            mDesc = (TextView) view.findViewById(R.id.textView14);
            mIcon = (ImageView) view.findViewById(R.id.imageView6);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTempF.getText() + "'";
        }
    }
}
