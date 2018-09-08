package com.example.a11637.ljhweather.db;

import org.litepal.crud.DataSupport;

//litepal实体类“市”,需加入litepal.xml文件list（映射列表）中

public class City extends DataSupport {
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }



    private String cityName;
    private int cityCode;

    public int getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public int getCityCode() {
        return cityCode;
    }


    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    private int provinceId;

}
