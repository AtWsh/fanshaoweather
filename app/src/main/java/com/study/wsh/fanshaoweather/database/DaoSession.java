package com.study.wsh.fanshaoweather.database;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.study.wsh.fanshaoweather.database.bean.City;
import com.study.wsh.fanshaoweather.database.bean.County;
import com.study.wsh.fanshaoweather.database.bean.Province;

import com.study.wsh.fanshaoweather.database.CityDao;
import com.study.wsh.fanshaoweather.database.CountyDao;
import com.study.wsh.fanshaoweather.database.ProvinceDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig cityDaoConfig;
    private final DaoConfig countyDaoConfig;
    private final DaoConfig provinceDaoConfig;

    private final CityDao cityDao;
    private final CountyDao countyDao;
    private final ProvinceDao provinceDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        cityDaoConfig = daoConfigMap.get(CityDao.class).clone();
        cityDaoConfig.initIdentityScope(type);

        countyDaoConfig = daoConfigMap.get(CountyDao.class).clone();
        countyDaoConfig.initIdentityScope(type);

        provinceDaoConfig = daoConfigMap.get(ProvinceDao.class).clone();
        provinceDaoConfig.initIdentityScope(type);

        cityDao = new CityDao(cityDaoConfig, this);
        countyDao = new CountyDao(countyDaoConfig, this);
        provinceDao = new ProvinceDao(provinceDaoConfig, this);

        registerDao(City.class, cityDao);
        registerDao(County.class, countyDao);
        registerDao(Province.class, provinceDao);
    }
    
    public void clear() {
        cityDaoConfig.clearIdentityScope();
        countyDaoConfig.clearIdentityScope();
        provinceDaoConfig.clearIdentityScope();
    }

    public CityDao getCityDao() {
        return cityDao;
    }

    public CountyDao getCountyDao() {
        return countyDao;
    }

    public ProvinceDao getProvinceDao() {
        return provinceDao;
    }

}
