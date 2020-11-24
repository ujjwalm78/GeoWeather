package com.example.geoweather;

public class Weather {

    private double temp;
    private double min_temp;
    private double max_temp;
    private long timeInMilliseconds;
    private long sunriseinMilliseconds;
    private long sunsetinMilliseconds;
    private String description;
    private int pressure;
    private double humidity;
    private double windSpeed;
    private String cityName;

    public Weather(double temp, double min_temp, double max_temp, long timeInMilliseconds, long sunriseinMilliseconds,
                   long sunsetinMilliseconds, String description, int pressure, double humidity, double windSpeed, String cityName) {
        this.temp = temp;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.timeInMilliseconds = timeInMilliseconds;
        this.sunriseinMilliseconds = sunriseinMilliseconds;
        this.sunsetinMilliseconds = sunsetinMilliseconds;
        this.description = description;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.cityName = cityName;
    }

    public double getTemp() {
        return temp;
    }

    public String getMin_temp() {
        String s = "Min Temp" + " " + min_temp + "°C";
        return s;
    }

    public String getMax_temp() {
        String s = "Max Temp" + " " +  max_temp + "°C";
        return s;
    }

    public long getTimeInMilliseconds() {
        return timeInMilliseconds;
    }

    public long getSunriseinMilliseconds() {
        return sunriseinMilliseconds;
    }

    public long getSunsetinMilliseconds() {
        return sunsetinMilliseconds;
    }

    public String getDescription() {
        return description;
    }

    public int getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getCityName() {
        return cityName;
    }
}
