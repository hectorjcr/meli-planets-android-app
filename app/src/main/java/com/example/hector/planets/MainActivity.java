package com.example.hector.planets;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.hector.planets.Utilities.CalcsUtils;
import com.example.hector.planets.pojos.DayReport;
import com.example.hector.planets.pojos.Planet;
import com.example.hector.planets.services.ForecastApiInterface;
import com.example.hector.planets.views.CustomView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {
    private int t=0;
    private Planet ferengi = new Planet("ferengi",500,1,t, Color.RED,Color.BLACK);
    private Planet betasoide = new Planet("betasoide",2000,3,t,Color.BLUE,Color.BLACK);
    private Planet vulcano =  new Planet("vulcano",1000,-5,t,Color.YELLOW,Color.BLACK);
    private Planet sun = new Planet("sun",0,0,t,Color.WHITE,Color.YELLOW);
    private List<Planet> planets = new ArrayList<Planet>();
    CustomView customView;
    Button increaseBtn,decreaseBtn;
    TextView timeText;
    EditText searchDay;
    TextView resultTextView;
    TextView perimeterTextView;
    ImageButton imageButton;

    TextView ferengiTextView;
    TextView betaTextView;
    TextView vulcanTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        increaseBtn = (Button)findViewById(R.id.increase_button);
        decreaseBtn = (Button)findViewById(R.id.decrease_button);
        imageButton = (ImageButton)findViewById(R.id.imageButton);

        timeText = (TextView)findViewById(R.id.time_text);
        timeText.setText("Dia: " + Integer.toString(this.t));
        searchDay = (EditText)findViewById(R.id.searchText);
        resultTextView = (TextView)findViewById(R.id.result_text_view);
        resultTextView.setText("");
        perimeterTextView = findViewById(R.id.perimeter_text_view);
        perimeterTextView.setText("");

        ferengiTextView = findViewById(R.id.ferengi_text_view);
        betaTextView = findViewById(R.id.betasoide_text_view);
        vulcanTextView = findViewById(R.id.vulcano_text_view);

        planets.add(ferengi);planets.add(betasoide);planets.add(vulcano);
        customView = (CustomView)findViewById(R.id.custom_view);
        customView.setPlanets(planets);
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incresaTime();
            }
        });
        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseTime();
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://meli-forecast-db-microservice.herokuapp.com/api/v1/forecast/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ForecastApiInterface forecastApiInterface = retrofit.create(ForecastApiInterface.class);

                Call<DayReport> call = forecastApiInterface.getDayForecast(searchDay.getText().toString());
                call.enqueue(new Callback<DayReport>() {
                    @Override
                    public void onResponse(Call<DayReport> call, Response<DayReport> response) {
                        if(!response.isSuccessful()){
                            resultTextView.setText("Code: "+response.code());
                            return;
                        }
                        DayReport dayReport = response.body();
                        resultTextView.setText("Reporte del dia: " + dayReport.getDay()+" Clima: " + dayReport.getWeather());
                        t = Integer.parseInt(searchDay.getText().toString());
                        updateTime(t);
                    }

                    @Override
                    public void onFailure(Call<DayReport> call, Throwable t) {
                        resultTextView.setText("Error: Información disponible hasta 3600 dias");
                    }
                });
            }
        });

    }
    public void incresaTime(){
        this.t++;
        updateTime(this.t);
    }
    private void decreaseTime(){
        if(t>0)
            this.t--;
        updateTime(t);
    }

    private void updateTime(int t){
        customView.updatePlanetsTime(this.t);
        updatePlanetsTime();
        if(!CalcsUtils.isCollinear(planets)){
            perimeterTextView.setText("Perimetro: " + Float.toString(CalcsUtils.perimeter(planets)));
        }
        else{
            perimeterTextView.setText("Perimetro: 0");
        }
        timeText.setText("Dia: " + Integer.toString(this.t));
        searchDay.setText(Integer.toString(t));
        ferengiTextView.setText("Ferengi\n\t" +
                "X: " + Float.toString(ferengi.getX()) + "\n\t" +
                "Y: " + Float.toString(ferengi.getY()) + "\n\t");
        betaTextView.setText("Betasoide\n\t" +
                "X: " + Float.toString(betasoide.getX()) + "\n\t" +
                "Y: " + Float.toString(betasoide.getY()) + "\n\t");
        vulcanTextView.setText("Vulcano\n\t" +
                "X: " + Float.toString(vulcano.getX()) + "\n\t" +
                "Y: " + Float.toString(vulcano.getY()) + "\n\t");

    }
    private void updatePlanetsTime(){
        ferengi.setT(this.t);
        betasoide.setT(this.t);
        vulcano.setT(this.t);
    }
}
