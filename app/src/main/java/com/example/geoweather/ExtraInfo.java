package com.example.geoweather;

public class ExtraInfo {

    private String description;
    private int pressure;
    private int humidity;
    private int windSpeed;
    private String cityName;

    public ExtraInfo(String description, int pressure, int humidity, int windSpeed, String cityName) {
        this.description = description;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public String getCityName() {
        return cityName;
    }
}
