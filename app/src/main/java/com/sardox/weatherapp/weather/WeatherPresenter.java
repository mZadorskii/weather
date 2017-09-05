package com.sardox.weatherapp.weather;


public interface WeatherPresenter {
    void onViewPrepared();

    //void updateRepo(List<OpenSourceResponse.Repo> repoList);
    void onAttach(WeatherView view);

    void onDetach();

    void onNetworkError(String error);

    void getWeatherByUserInput(String user_input);

    void onLocationPermissionGranted();

    void onLocationPermissionDenied();

    void getWeatherWithMyLocation();
}
