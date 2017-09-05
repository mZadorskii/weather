package com.sardox.weatherapp.recents;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sardox.weatherapp.R;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sardox on 9/2/2017.
 */

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.MyRecentItemHolder> {
    private static final String TAG = "RecentAdapter";

    private List<RecentItem> recents;
    private Callback mCallback;

    @Inject
    public RecentAdapter(List<RecentItem> recents) {
        this.recents = recents;
    }

    class MyRecentItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recent_location_text)
        TextView item_location;

        public MyRecentItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public MyRecentItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyler_recent_item, parent, false);
        return new MyRecentItemHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyRecentItemHolder holder, int position) {
        if (holder.item_location != null)
            holder.item_location.setText(recents.get(position).getFriendlyLocationName() + " searched by " + recents.get(position).getMyCacheKey().getClass().getSimpleName());
        final RecentItem item = recents.get(position);
        if (holder.itemView != null) holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onRecentClicked(item);
            }
        });
    }

    public void addItems(List<RecentItem> newRecents) {
        Log.v("weatherApp", TAG + " received new items:" + newRecents.size());
        recents.clear();
        recents.addAll(newRecents);
        Log.v("weatherApp", TAG + " new list size:" + recents.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return recents.size();
    }

    public void setCallback(Callback mCallback) {
        this.mCallback = mCallback;
    }

    public interface Callback {
        void onRecentClicked(RecentItem item);
    }
}
