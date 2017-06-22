package com.study.wsh.fanshaoweather.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wenshenghui on 2017/6/22.
 * 城市
 */

@Entity
public class City {

    @Id
    private Long id;

    @Property @Unique
    private String cityName;

    @Property
    private int cityId;

    @Property
    private int provinceId;

    @Generated(hash = 543626715)
    public City(Long id, String cityName, int cityId, int provinceId) {
        this.id = id;
        this.cityName = cityName;
        this.cityId = cityId;
        this.provinceId = provinceId;
    }

    @Generated(hash = 750791287)
    public City() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityId() {
        return this.cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getProvinceId() {
        return this.provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
