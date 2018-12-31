package com.example.hector.planets.services;

import com.example.hector.planets.pojos.DayReport;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ForecastApiInterface {

    @GET(value = "day/{day}")
    Call<DayReport> getDayForecast(@Path("day") String day);
}
