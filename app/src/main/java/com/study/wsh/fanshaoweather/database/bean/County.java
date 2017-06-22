package com.study.wsh.fanshaoweather.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wenshenghui on 2017/6/22.
 * 县区
 */

@Entity
public class County {

    @Id
    private Long id;

    @Property @Unique
    private String countyName;

    @Property
    private int weatherId;

    @Property
    private int cityId;

    @Generated(hash = 451909194)
    public County(Long id, String countyName, int weatherId, int cityId) {
        this.id = id;
        this.countyName = countyName;
        this.weatherId = weatherId;
        this.cityId = cityId;
    }

    @Generated(hash = 1991272252)
    public County() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountyName() {
        return this.countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getWeatherId() {
        return this.weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return this.cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
