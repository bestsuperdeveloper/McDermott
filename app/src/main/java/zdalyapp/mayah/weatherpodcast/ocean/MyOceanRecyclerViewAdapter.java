package zdalyapp.mayah.weatherpodcast.ocean;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import zdalyapp.mayah.R;
import zdalyapp.mayah.weatherpodcast.ocean.dummy.DummyContent;



public class MyOceanRecyclerViewAdapter extends RecyclerView.Adapter<MyOceanRecyclerViewAdapter.ViewHolder> {

    private final List<DummyContent.OceanItem> mValues;
    private final OceanFragment.OnOceanFragmentInteractionListener mListener;

    public MyOceanRecyclerViewAdapter(List<DummyContent.OceanItem> items, OceanFragment.OnOceanFragmentInteractionListener listener) {
        mValues = items;
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
        holder.mItem = mValues.get(position);

        holder.mTitle.setText(holder.mItem.title);
        holder.mLatitude.setText(holder.mItem.latitude);
        holder.mLongitude.setText(holder.mItem.longitude);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onOceanFragmentInteraction(holder.mItem);
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
        public  TextView mLatitude;
        public  TextView mLongitude;

        public DummyContent.OceanItem mItem;

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
