package com.example.a11637.ljhweather.gson;

import com.google.gson.annotations.SerializedName;

public class Basic {
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    @SerializedName("updata")
    public Updata updata;

    public class Updata{
        @SerializedName("loc")
        public String updataTime;
    }
}
