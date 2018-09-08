package com.example.a11637.ljhweather.gson;

import com.google.gson.annotations.SerializedName;

public class AQI {

    @SerializedName("city")
    public AQICity city;

    public class AQICity{
        @SerializedName("aqi")
        public String aqi;

        @SerializedName("pm25")
        public String pm25;
    }
}
