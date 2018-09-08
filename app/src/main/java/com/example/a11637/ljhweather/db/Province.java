package com.example.a11637.ljhweather.db;

import org.litepal.crud.DataSupport;

//litepal实体类“省”,需加入litepal.xml文件list（映射列表）中

public class Province extends DataSupport {
    public int getId() {
        return id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }



    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String provinceName;
    private int provinceCode;


}
