package com.sardox.weatherapp.recents;

import com.sardox.weatherapp.model.MainModel;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;
import com.sardox.weatherapp.utils.Consumer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by sardox on 9/3/2017.
 */
public class RecentPresenterImplTest {

    RecentView recentView;
    MainModel mainModel;
    private RecentPresenterImpl recentPresenter;

    @Before
    public void setUp() throws Exception {
        mainModel = mock(MainModel.class);
        recentView = mock(RecentView.class);
        recentPresenter = new RecentPresenterImpl(mainModel);
        recentPresenter.onAttach(recentView);
    }

    @Test
    public void onRecentClicked() throws Exception {

    }

    @Test
    public void onNetworkError() throws Exception {

    }

    @Test
    public void onNewRecentAdded() throws Exception {

    }

    @Test
    public void onViewPrepared() throws Exception {
       // mainModel.setupRecentCallbacks(recentPresenter);
        List<RecentItem> items = new ArrayList<>();
        items.add(new RecentItem());

        doReturn(items).when(mainModel).getRecentItems(new Consumer<List<RecentItem>>() {
            @Override
            public void onSuccess(List<RecentItem> var1) {

            }

            @Override
            public void onFailed(String error) {

            }
        });
        recentPresenter.onViewPrepared();
        verify(recentView).showLoading();

//        verify(mainModel).getRecentItems(new Consumer<List<RecentItem>>() {
//            @Override
//            public void onSuccess(List<RecentItem> var1) {
//
//            }
//
//            @Override
//            public void onFailed(String error) {
//
//            }
//        });
    }

}