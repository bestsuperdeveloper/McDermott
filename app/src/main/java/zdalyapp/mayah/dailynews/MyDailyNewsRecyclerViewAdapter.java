package zdalyapp.mayah.dailynews;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import zdalyapp.mayah.R;
import zdalyapp.mayah.dailynews.DailyNewsFragment.OnListDailyNewsInteractionListener;
import zdalyapp.mayah.dailynews.dummy.DummyContent;
import zdalyapp.mayah.dailynews.dummy.DummyContent.DailyNewsItem;

import java.util.List;

public class MyDailyNewsRecyclerViewAdapter extends RecyclerView.Adapter<MyDailyNewsRecyclerViewAdapter.ViewHolder> {

    private final List<DummyContent.DailyNewsItem> mValues;
    private final DailyNewsFragment.OnListDailyNewsInteractionListener mListener;
    private boolean searchflag = false;
    public MyDailyNewsRecyclerViewAdapter(List<DummyContent.DailyNewsItem> items, DailyNewsFragment.OnListDailyNewsInteractionListener listener, boolean searchflag) {
        mValues = items;
        mListener = listener;
        this.searchflag = searchflag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_cell_daily_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
        holder.mDetailView.setText(mValues.get(position).details);
        if (searchflag)
        {
            holder.mDateView.setText(mValues.get(position).strDate);
            holder.chkButton.setVisibility(View.VISIBLE);
            holder.shareButton.setVisibility(View.GONE);
        }
        else
        {
            holder.chkButton.setVisibility(View.GONE);
            holder.mDateView.setVisibility(View.GONE);
            holder.shareButton.setVisibility(View.VISIBLE);
        }
        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListShareInteraction(holder.mItem);
                }

            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
        holder.chkButton.setOnCheckedChangeListener(null);
        holder.chkButton.setChecked(holder.mItem.isSelected);
        holder.chkButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                holder.mItem.isSelected = b;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mDetailView;
        public final TextView mDateView;
        public final ImageButton shareButton;
        public final CheckBox chkButton;
        public DailyNewsItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view.findViewById(R.id.mainLayout);
            mIdView = (TextView) view.findViewById(R.id.textView1);
            mContentView = (TextView) view.findViewById(R.id.textView2);
            mDetailView = (TextView) view.findViewById(R.id.textView3);
            shareButton = (ImageButton) view.findViewById(R.id.imageButton);
            mDateView = (TextView) view.findViewById(R.id.textView4);
            chkButton = (CheckBox) view.findViewById(R.id.checkBox);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
