package com.study.wsh.fanshaoweather.gson;

/**
 * Created by wenshenghui on 2017/6/22.
 */

public class AQI {

    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
