package com.study.wsh.fanshaoweather.application;

import android.app.Application;
import android.content.Context;

import com.study.wsh.fanshaoweather.database.helper.GreenDaoDatabase;

/**
 * Created by wenshenghui on 2017/6/22.
 */

public class FanshaoApplication extends Application {

    private static Context sApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = getApplicationContext();
        GreenDaoDatabase.getInstance().initDatabase();
    }

    public static Context getApplication() {
        return sApplication;
    }

}
