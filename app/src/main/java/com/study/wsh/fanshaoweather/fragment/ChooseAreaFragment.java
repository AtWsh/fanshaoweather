package com.study.wsh.fanshaoweather.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.study.wsh.fanshaoweather.R;
import com.study.wsh.fanshaoweather.activity.WeatherActivity;
import com.study.wsh.fanshaoweather.database.CityDao;
import com.study.wsh.fanshaoweather.database.CountyDao;
import com.study.wsh.fanshaoweather.database.DaoSession;
import com.study.wsh.fanshaoweather.database.bean.City;
import com.study.wsh.fanshaoweather.database.bean.County;
import com.study.wsh.fanshaoweather.database.bean.Province;
import com.study.wsh.fanshaoweather.database.helper.GreenDaoDatabase;
import com.study.wsh.fanshaoweather.okhttp.HttpUtil;
import com.study.wsh.fanshaoweather.okhttp.WeatherHttpParse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseAreaFragment extends Fragment {

    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String COUNTY = "county";
    public static final String URL_API_CHINA = "http://guolin.tech/api/china";

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    private int mCurrentLevel;

    private TextView mTitleView;
    private Button mBackBtn;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private List<String> mDataList = new ArrayList<>();

    private List<Province> mProvinceList;
    private List<City> mCityList;
    private List<County> mCountyList;

    private Province mSelectProvince;
    private City mSelectCity;

    private DaoSession mDaoSession;

    private ProgressDialog mProgressDialog;

    public ChooseAreaFragment() {}

    public static ChooseAreaFragment newInstance() {
        ChooseAreaFragment fragment = new ChooseAreaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_area, container, false);
        initView(view);

        initGreendaoSession();
        return view;
    }

    private void initGreendaoSession() {
        mDaoSession = GreenDaoDatabase.getInstance().getDaoSession();
    }

    private void initView(View view) {
        mTitleView = (TextView)view.findViewById(R.id.title_text);
        mBackBtn = (Button)view.findViewById(R.id.back_button);
        mListView = (ListView)view.findViewById(R.id.list_view);
        mAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,mDataList);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mCurrentLevel == LEVEL_PROVINCE && mProvinceList != null){
                    mSelectProvince = mProvinceList.get(position);
                    queryCities();
                }else if(mCurrentLevel == LEVEL_CITY && mCityList != null){
                    mSelectCity = mCityList.get(position);
                    queryCounties();
                } else if (mCurrentLevel == LEVEL_COUNTY){
                    String weatherId = mCountyList.get(position).getWeatherId();
                    Intent intent = new Intent(getActivity(), WeatherActivity.class);
                    intent.putExtra("weather_id",weatherId);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentLevel == LEVEL_COUNTY){
                    queryCities();
                }else if(mCurrentLevel == LEVEL_CITY){
                    queryProVinces();
                }
            }
        });

        queryProVinces();
    }

    private void queryProVinces() {
        mTitleView.setText(R.string.china);
        mBackBtn.setVisibility(View.GONE);
        mProvinceList = mDaoSession.getProvinceDao().queryBuilder().list();
        if(mProvinceList.size() > 0){
            mDataList.clear();
            for (Province province : mProvinceList) {
                mDataList.add(province.getProvinceName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            mCurrentLevel = LEVEL_PROVINCE;
        }else {
            queryFromServer(URL_API_CHINA, PROVINCE);
        }
    }

    private void queryFromServer(String address, final String type) {
        showProgressDialog();
        HttpUtil.sendOKHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if(PROVINCE.equals(type)){
                    result = WeatherHttpParse.handleProvinceResponse(responseText);
                }else if(CITY.equals(type)){
                    result = WeatherHttpParse.handleCityResponse(responseText,mSelectProvince.getProvinceId());
                }else if(COUNTY.equals(type)){
                    result = WeatherHttpParse.handleCountyResponse(responseText,mSelectCity.getCityId());
                }

                if(result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if(PROVINCE.equals(type)){
                                queryProVinces();
                            }else if(CITY.equals(type)){
                                queryCities();
                            }else if(COUNTY.equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });
    }

    private void closeProgressDialog() {
        if(mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("正在加载...");
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    private void queryCounties() {
        mTitleView.setText(mSelectCity.getCityName());
        mBackBtn.setVisibility(View.VISIBLE);
        mCountyList = mDaoSession.getCountyDao().queryBuilder().where(CountyDao.Properties.CityId.eq(mSelectCity.getCityId())).list();
        if(mCountyList.size() > 0){
            mDataList.clear();
            for (County county : mCountyList) {
                mDataList.add(county.getCountyName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            mCurrentLevel = LEVEL_COUNTY;
        }else {
            int provinceId = mSelectProvince.getProvinceId();
            int cityId = mSelectCity.getCityId();
            String address = URL_API_CHINA + "/" + provinceId + "/" + cityId;
            queryFromServer(address, COUNTY);
        }
    }

    private void queryCities() {
        mTitleView.setText(mSelectProvince.getProvinceName());
        mBackBtn.setVisibility(View.VISIBLE);
        mCityList = mDaoSession.getCityDao().queryBuilder().where(CityDao.Properties.ProvinceId.eq(mSelectProvince.getProvinceId())).list();
        if(mCityList.size() > 0){
            mDataList.clear();
            for (City city : mCityList) {
                mDataList.add(city.getCityName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            mCurrentLevel = LEVEL_CITY;
        }else {
            int provinceId = mSelectProvince.getProvinceId();
            String address = URL_API_CHINA + "/" + provinceId;
            queryFromServer(address, CITY);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
