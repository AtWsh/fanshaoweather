package com.study.wsh.fanshaoweather.database.helper;

import android.database.sqlite.SQLiteDatabase;

import com.study.wsh.fanshaoweather.application.FanshaoApplication;
import com.study.wsh.fanshaoweather.database.DaoMaster;
import com.study.wsh.fanshaoweather.database.DaoSession;

/**
 * Created by wenshenghui on 2017/6/22.
 *
 * 数据库 greendao.db 初始化操作类
 */

public class GreenDaoDatabase {

    private static volatile GreenDaoDatabase sInstance;

    private DaoSession mDaoSession;

    private static final String GREEN_DAO_DB_NAME = "greendao.db";

    private GreenDaoDatabase() {
    }

    public static GreenDaoDatabase getInstance() {
        if(sInstance == null) {
            synchronized (GreenDaoDatabase.class) {
                if (sInstance == null) {
                    sInstance = new GreenDaoDatabase();
                }
            }
        }
        return sInstance;
    }

    /**
     * 配置数据库
     */
    public void initDatabase() {
        //创建数据库GREEN_DAO_DB_NAME"     GreenDaoOpenHelper：创建SQLite数据库的SQLiteOpenHelper的具体实现
        GreenDaoOpenHelper helper = new GreenDaoOpenHelper(FanshaoApplication.getApplication(), GREEN_DAO_DB_NAME);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象   DaoMaster为 GreenDao的顶级对象，作为数据库对象、用于创建表和删除表
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者   DaoSession：管理所有的Dao对象，Dao对象中存在着增删改查等API
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

}
