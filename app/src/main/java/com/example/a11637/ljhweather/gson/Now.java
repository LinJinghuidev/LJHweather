package com.example.a11637.ljhweather.gson;

import com.google.gson.annotations.SerializedName;

public class Now {
    @SerializedName("tmp")
    public String tmp;
    @SerializedName("cond")
    public Cond more;

    public class Cond{
        @SerializedName("txt")
        public String info;
    }
}
