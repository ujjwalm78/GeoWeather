package com.example.geoweather;

public class DateTime {
    private long timeInMilliseconds;
    private long sunriseinMilliseconds;
    private long sunsetinMilliseconds;

    public DateTime(long timeInMilliseconds, long sunriseinMilliseconds, long sunsetinMilliseconds) {
        this.timeInMilliseconds = timeInMilliseconds;
        this.sunriseinMilliseconds = sunriseinMilliseconds;
        this.sunsetinMilliseconds = sunsetinMilliseconds;
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
}
