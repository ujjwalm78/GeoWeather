package com.example.geoweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    public static String query;
    ImageView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        search = (ImageView) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the AsyncTask to fetch the earthquake data
                WeatherAsyncTask task = new WeatherAsyncTask();
                task.execute(concatenateString(editText.getText().toString()));
            }
        });
    }

    private String concatenateString(String area){
        return "http://api.openweathermap.org/data/2.5/weather?q=" + area + "&appid=796594297ce4874c6d27f4abb33744b4&units=metric";
    }

    /**
     * Update the UI with the given earthquake information.
     */
    private void updateUi(Weather weather) {
        TextView titleTextView = (TextView) findViewById(R.id.CITYNAME);
        titleTextView.setText(weather.getCityName());

        TextView descriptionTextView = (TextView) findViewById(R.id.DESCRIPTION);
        descriptionTextView.setText(weather.getDescription());

        TextView tempTextView = (TextView) findViewById(R.id.TEMP);

        String formattedTemp = formatMagnitude(weather.getTemp());
        tempTextView.setText(formattedTemp + "°C");

        TextView max_tempTextView = (TextView) findViewById(R.id.MAXTEMP);
        max_tempTextView.setText(weather.getMax_temp());

        TextView min_tempTextView = (TextView) findViewById(R.id.MINTEMP);
        min_tempTextView.setText(weather.getMin_temp());

        TextView pressureTextView = (TextView) findViewById(R.id.PressureText);
        pressureTextView.setText(Integer.toString(weather.getPressure()) + " Pa");

        TextView windTextView = (TextView) findViewById(R.id.WindText);
        windTextView.setText(Double.toString(weather.getWindSpeed()) + "mph");

        TextView humidityTextView = (TextView) findViewById(R.id.HumidityText);
        humidityTextView.setText(Double.toString(weather.getHumidity()) + "%");

        Date dateObject = new Date(weather.getTimeInMilliseconds() * 1000L);

        TextView dateTextView = (TextView) findViewById(R.id.DATE);

        String formattedDate = formatDate(dateObject);
        dateTextView.setText(formattedDate);

        TextView timeTextView = (TextView) findViewById(R.id.TIME);

        String formattedTime = formatTime(dateObject);
        timeTextView.setText(formattedTime);

        Date sunRiseObject = new Date(weather.getSunriseinMilliseconds() * 1000L);

        TextView sunriseTextView = (TextView) findViewById(R.id.SunriseText);

        String sunriseformattedTime = formatTime(sunRiseObject);
        sunriseTextView.setText(sunriseformattedTime);

        Date sunSetObject = new Date(weather.getSunsetinMilliseconds() * 1000L);

        TextView sunsetTextView = (TextView) findViewById(R.id.SunsetText);

        String sunsetformattedTime = formatTime(sunSetObject);
        sunsetTextView.setText(sunsetformattedTime);


    }
    private class WeatherAsyncTask extends AsyncTask<String , Void , Weather> {

        @Override
        protected Weather doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            // Perform the HTTP request for earthquake data and process the response.
            Weather result = WebConnection.fetchWeatherData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(Weather result) {

            if (result == null) {
                return;
            }
            updateUi(result);
        }
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

}
