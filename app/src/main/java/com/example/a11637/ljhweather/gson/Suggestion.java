package com.example.a11637.ljhweather.gson;

import com.google.gson.annotations.SerializedName;

public class Suggestion {
    @SerializedName("comf")
    public Comf comf;
    public class Comf{
        @SerializedName("txt")
        public String info;
    }
    @SerializedName("cw")
    public Cw cw;
    public class Cw{
        @SerializedName("txt")
        public String info;
    }
    @SerializedName("sport")
    public Sport sport;
    public class Sport{
        @SerializedName("txt")
        public String info;
    }

}
