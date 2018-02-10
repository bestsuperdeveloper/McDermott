package zdalyapp.mayah.spotprices;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import zdalyapp.mayah.R;
import zdalyapp.mayah.spotprices.SpotPriceFragment.OnSpotListFragmentInteractionListener;
import zdalyapp.mayah.spotprices.dummy.DummyContent.SpotPriceItem;

import java.util.List;


public class MySpotPriceRecyclerViewAdapter extends RecyclerView.Adapter<MySpotPriceRecyclerViewAdapter.ViewHolder> {

    private final List<SpotPriceItem> mValues;
    private final OnSpotListFragmentInteractionListener mListener;

    public MySpotPriceRecyclerViewAdapter(List<SpotPriceItem> items, OnSpotListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_cell_spot_price, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.textView1.setText(holder.mItem.id);
        holder.textView2.setText(mValues.get(position).content);

        holder.textView4.setText(mValues.get(position).details2);
        if (holder.mItem.details1.equals("")) {
            holder.textView3.setVisibility(View.GONE);
        }
        else
        {
           // String string = String.format("%.2f", Double.parseDouble(mValues.get(position).details1));
            holder.textView3.setText(mValues.get(position).details1);
        }
        if (holder.mItem.details2.equals(""))
            holder.textView4.setVisibility(View.GONE);
        else {

        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onSpotListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("mValues", "" + mValues.size());
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textView1;
        public final TextView textView2;
        public final TextView textView3;
        public final TextView textView4;
        public SpotPriceItem mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            textView1 = (TextView) view.findViewById(R.id.textView4);
            textView2 = (TextView) view.findViewById(R.id.textView8);
            textView3 = (TextView) view.findViewById(R.id.textView5);
            textView4 = (TextView) view.findViewById(R.id.textView6);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textView1.getText() + "'";
        }
    }
}
