package com.study.wsh.fanshaoweather.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wenshenghui on 2017/6/22.
 * 省份
 */

@Entity
public class Province {

    @Id
    private Long id;

    @Property @Unique
    private String provinceName;

    @Property
    private int provinceId;

    @Generated(hash = 1431247227)
    public Province(Long id, String provinceName, int provinceId) {
        this.id = id;
        this.provinceName = provinceName;
        this.provinceId = provinceId;
    }

    @Generated(hash = 1309009906)
    public Province() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvinceName() {
        return this.provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceId() {
        return this.provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
