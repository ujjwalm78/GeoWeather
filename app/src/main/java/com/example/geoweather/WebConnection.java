package com.example.geoweather;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class WebConnection {

    public static final String LOG_TAG = MainActivity.class.getName();

    /**
     * Query the USGS dataset and return an {@link Weather} object to represent a single earthquake.
     */
    public static Weather fetchWeatherData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Weather} object
        Weather weather = extractFeatureFromJson(jsonResponse);

        // Return the {@link Weather}
        return weather;
    }
    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return an {@link Weather} object by parsing out information
     * about the first earthquake from the input earthquakeJSON string.
     */
    private static Weather extractFeatureFromJson(String weatherJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(weatherJSON)) {
            return null;
        }

        try {
            JSONObject baseJsonResponse = new JSONObject(weatherJSON);

            JSONArray weather = baseJsonResponse.getJSONArray("weather");
            JSONObject maindesc = weather.getJSONObject(0);
            String description = maindesc.getString("main");

            JSONObject main = baseJsonResponse.getJSONObject("main");
            double temp = main.getDouble("temp");
            double min_temp = main.getDouble("temp_min");
            double max_temp = main.getDouble("temp_max");
            int pressure = main.getInt("pressure");
            double humidity = main.getDouble("humidity");

            JSONObject wind = baseJsonResponse.getJSONObject("wind");
            double windSpeed = wind.getDouble("speed");

            long timeInMilliseconds = baseJsonResponse.getLong("dt");

            JSONObject sys = baseJsonResponse.getJSONObject("sys");
            long sunriseinMilliseconds = sys.getLong("sunrise");
            long sunsetinMilliseconds = sys.getLong("sunset");

            String cityName = baseJsonResponse.getString("name");

            return new Weather(temp, min_temp, max_temp, timeInMilliseconds, sunriseinMilliseconds,
             sunsetinMilliseconds, description, pressure, humidity, windSpeed, cityName);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return null;
    }
}
