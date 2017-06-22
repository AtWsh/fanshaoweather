package com.study.wsh.fanshaoweather.database.helper;

import android.content.Context;

import com.study.wsh.fanshaoweather.database.DaoMaster;
import com.study.wsh.fanshaoweather.database.ProvinceDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by wenshenghui on 2017/6/22.
 */

public class GreenDaoOpenHelper extends DaoMaster.OpenHelper {

    public GreenDaoOpenHelper(Context context, String name) {
        super(context, name);
    }

    /**
     * 数据库升级
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //操作数据库的更新
        //依据每个版本的更新需求，只需要将需要升级的实体数据库进行如下操作：便可保留原有数据，然后根据新的bean类创建新表

        if(newVersion == 3) {
            GreenDaoUpdateHelper.migrate(db, ProvinceDao.class);
        }
    }
}
