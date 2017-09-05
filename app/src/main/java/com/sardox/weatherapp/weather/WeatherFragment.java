package com.sardox.weatherapp.weather;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sardox.weatherapp.Dagger.ActivityComponent;
import com.sardox.weatherapp.R;
import com.sardox.weatherapp.main.MainActivity;
import com.sardox.weatherapp.utils.WeatherForecast;

import java.text.DecimalFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class WeatherFragment extends Fragment implements WeatherView {
    private static final String TAG = "WeatherFragment";
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 4568;
    private MainActivity activity;
    private final DecimalFormat tempFormat = new DecimalFormat("#.#");
    private final DecimalFormat humidityFormat = new DecimalFormat("#");
    Unbinder unbinder;


    @Inject
    WeatherPresenter mPresenter;

    @BindView(R.id.text_humidity)
    TextView text_humidity;

    @BindView(R.id.edit_user_input)
    EditText edit_user_input;

    @BindView(R.id.weather_progress)
    ProgressBar weather_progress;

    @BindView(R.id.button_search)
    Button button_search;

    @OnClick(R.id.button_search)
    void searchButtonClicked(View view) {
        String user_input = edit_user_input.getText().toString();
        hideKeyboard(view);
        mPresenter.getWeatherByUserInput(user_input);
    }

    @OnClick(R.id.button_gps)
    void searchUsingMyLocation(View view) {
        mPresenter.getWeatherWithMyLocation();
    }


    @BindView(R.id.text_location)
    TextView text_location;

    @BindView(R.id.text_temp)
    TextView text_temp;

    @BindView(R.id.text_weather_descr)
    TextView text_weather_descr;


    public WeatherFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static WeatherFragment newInstance() {
        Bundle args = new Bundle();
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("weatherApp", TAG + " onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        ActivityComponent activityComponent = activity.getmActivityComponent();
        if (activityComponent != null) {
            activityComponent.inject(this);
            unbinder = ButterKnife.bind(this, rootView);
            mPresenter.onAttach(this);
        }
        mPresenter.onViewPrepared();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        Log.v("weatherApp", TAG + " onDestroyView");
        super.onDestroyView();
        mPresenter.onDetach();
        unbinder.unbind();
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
        weather_progress.setVisibility(View.VISIBLE);
        //Toast.makeText(getContext(), "Loading... Plz wait", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        weather_progress.setVisibility(View.GONE);
    }

    @Override
    public void updateWeather(WeatherForecast weatherForecast) {
        text_humidity.setText(String.format(getString(R.string.humidity), humidityFormat.format(weatherForecast.getHumidity())));
        text_temp.setText(String.format(getString(R.string.temperature), tempFormat.format(weatherForecast.getTemp())));
        text_location.setText(weatherForecast.getLocationName());
        text_weather_descr.setText(weatherForecast.getWeather_description());
    }

    @Override
    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
            } else {
                // No explanation needed, we can request the permission. if user clicked NEVER, popup wont be created and onPermissionDenied will be called
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
            }
        } else {
            onPermissionGranted();
        }
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), "Message: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onPermissionGranted();
                } else {
                    onPermissionDenied();
                }
            }
        }
    }

    private void onPermissionDenied() {
        mPresenter.onLocationPermissionDenied();
    }

    private void onPermissionGranted() {
        mPresenter.onLocationPermissionGranted();
    }

    @Override
    public void askUserToEnablePermissions() {
        Toast.makeText(getActivity(), "Location permission is disabled. Please enable", Toast.LENGTH_LONG).show();
        // new AppSettingsDialog.Builder(getActivity()).build().show();
    }

    @Override
    public void onNetworkProviderDisabled() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your Netwrok Provider seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        hideLoading();
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
