package com.sardox.weatherapp.recents;

import com.sardox.weatherapp.model.MainModel;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;
import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities.OpenWeatherForecast;
import com.sardox.weatherapp.utils.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by sardox on 9/3/2017.
 */
public class RecentPresenterImplTest {

    @Mock
    RecentView recentView;
    @Mock
    MainModel mainModel;

    private RecentPresenterImpl recentPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recentPresenter = new RecentPresenterImpl(mainModel);
        recentPresenter.onAttach(recentView);
    }

    @Test
    public void onRecentClicked() throws Exception {
    }


    @Test
    public void hideLoadingWasCalledWhenRecentRequested() throws Exception {
        final List<RecentItem> list = new ArrayList<>();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Consumer<List<RecentItem>>) invocation.getArguments()[0]).onSuccess(list);
                return null;
            }
        }).when(mainModel).getRecentItems(ArgumentMatchers.<Consumer>any());

        recentPresenter.onViewPrepared();
        verify(recentView).hideLoading();
    }

    @Test
    public void showLoadingWhenonViewPrepared() throws Exception {
        recentPresenter.onViewPrepared();
        verify(recentView).showLoading();
    }

}