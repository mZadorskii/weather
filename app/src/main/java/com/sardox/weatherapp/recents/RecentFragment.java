package com.sardox.weatherapp.recents;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sardox.weatherapp.Dagger.ActivityComponent;
import com.sardox.weatherapp.main.MainActivity;
import com.sardox.weatherapp.R;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class RecentFragment extends Fragment implements RecentView, RecentAdapter.Callback{

    private MainActivity activity;
    private static final String TAG = "RecentFragment";

//        @BindView(R.id.section_label)
//        TextView textView;

    @Inject
    RecentPresenter mPresenter;

    @Inject
    RecentAdapter recentAdapter;

    @BindView(R.id.recent_recycler_view)
    RecyclerView mRecyclerView;

    Unbinder unbinder;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public RecentFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RecentFragment newInstance() {
        Bundle args = new Bundle();
        RecentFragment fragment = new RecentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("weatherApp", TAG + " onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_recent, container, false);

        ActivityComponent activityComponent = activity.getmActivityComponent();
        if (activityComponent != null) {
            activityComponent.inject(this);
            unbinder = ButterKnife.bind(this, rootView);
            mPresenter.onAttach(this);
            recentAdapter.setCallback(this);
        }

        setup();
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }



    private void setup() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration( new DividerItemDecoration(getContext(),
                mLayoutManager.getOrientation()));
        mRecyclerView.setAdapter(recentAdapter);
        mPresenter.onViewPrepared();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings :
               removeItems();
             return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void removeItems() {
        mPresenter.removeItems(recentAdapter.getListOfSelectedItems());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        Log.v("weatherApp", TAG + " onDestroyView");
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            MainActivity activity = (MainActivity) context;
            this.activity = activity;
            // activity.onFragmentAttached();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void updateList(List<RecentItem> itemsToAdd) {
        Log.v("weatherApp", TAG + " updateList");
        recentAdapter.addItems(itemsToAdd);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(),"Error: " + message, Toast.LENGTH_SHORT).show();
        recentAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(),"message: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecentClicked(RecentItem clickedItem) {
        mPresenter.onRecentClicked(clickedItem);
    }
}
