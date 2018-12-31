package com.example.hector.planets.models;

import android.util.Log;

import com.example.hector.planets.pojos.DayReport;
import com.example.hector.planets.services.ForecastApiInterface;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DayForecastModel {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://meli-forecast-db-microservice.herokuapp.com/api/v1/forecast/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private ForecastApiInterface forecastApiInterface = retrofit.create(ForecastApiInterface.class);


    public DayReport getDayForecast(String t){
        final DayReport[] dayReport = new DayReport[1];
        Call<DayReport> call = forecastApiInterface.getDayForecast(t);
        call.enqueue(new Callback<DayReport>() {
            @Override
            public void onResponse(Call<DayReport> call, Response<DayReport> response) {
                if(!response.isSuccessful()){
                    Log.d("INFO","Code: "+ response.code());
                    return;
                }
                dayReport[0] = response.body();
            }

            @Override
            public void onFailure(Call<DayReport> call, Throwable t) {
                Log.e("INFO", "onFailure: " + t.getMessage());
            }
        });
        return dayReport[0];
    }
}
