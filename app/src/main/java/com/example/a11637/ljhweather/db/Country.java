package com.example.a11637.ljhweather.db;

import org.litepal.crud.DataSupport;

//litepal实体类“县”,需加入litepal.xml文件list（映射列表）中

public class Country extends DataSupport {
    private int id;

    public int getId() {
        return id;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    private String countryName;

    public void setId(int id) {
        this.id = id;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    private String weatherId;
    private int cityId;


}
