package com.example.a11637.ljhweather.gson;

import com.google.gson.annotations.SerializedName;

public class Forecast {
    @SerializedName("date")
    public String data;
    @SerializedName("cond")
    public Cond more;
    public class Cond{
        @SerializedName("txt_d")
        public String info;
    }
    @SerializedName("tmp")
    public Tmp tmp;
    public class Tmp{
        @SerializedName("max")
        public String max;
        @SerializedName("min")
        public String min;
    }
}
