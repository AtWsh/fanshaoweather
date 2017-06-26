package com.study.wsh.fanshaoweather.okhttp;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.study.wsh.fanshaoweather.database.CityDao;
import com.study.wsh.fanshaoweather.database.CountyDao;
import com.study.wsh.fanshaoweather.database.ProvinceDao;
import com.study.wsh.fanshaoweather.database.bean.City;
import com.study.wsh.fanshaoweather.database.bean.County;
import com.study.wsh.fanshaoweather.database.bean.Province;
import com.study.wsh.fanshaoweather.database.helper.GreenDaoDatabase;
import com.study.wsh.fanshaoweather.gson.Weather;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by wenshenghui on 2017/6/22.
 */

public class WeatherHttpParse {

    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            ProvinceDao provinceDao = GreenDaoDatabase.getInstance().getDaoSession().getProvinceDao();
            try{
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject jsonObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(jsonObject.getString("name"));
                    province.setProvinceId(jsonObject.getInt("id"));
                    provinceDao.insert(province);
                }
                return true;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCityResponse(String response, int provinceId){
        if(!TextUtils.isEmpty(response)){
            CityDao cityDao = GreenDaoDatabase.getInstance().getDaoSession().getCityDao();
            try{
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject jsonObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(jsonObject.getString("name"));
                    city.setCityId(jsonObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    cityDao.insert(city);
                }
                return true;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCountyResponse(String response, int cityId){
        if(!TextUtils.isEmpty(response)){
            CountyDao countyDao = GreenDaoDatabase.getInstance().getDaoSession().getCountyDao();
            try{
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {
                    JSONObject jsonObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(jsonObject.getString("name"));
                    county.setWeatherId(jsonObject.getString("weather_id"));
                    county.setCityId(cityId);
                    countyDao.insert(county);
                }
                return true;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static Weather handleWeatherResponse(String response) {
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
