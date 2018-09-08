package com.example.a11637.ljhweather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a11637.ljhweather.R;
import com.example.a11637.ljhweather.gson.Forecast;
import com.example.a11637.ljhweather.gson.Weather;
import com.example.a11637.ljhweather.service.AutoUpdateMyService;
import com.example.a11637.ljhweather.util.HttpUtil;
import com.example.a11637.ljhweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView swWeather;
    private TextView tvTitleCity;
    private TextView tvTitleUpdatTime;
    private TextView tvTmp;
    private TextView tvWeatherInfo;
    private LinearLayout llForecast;
    private TextView tvAqi;
    private TextView tvPm25;
    private TextView tvComfort;
    private TextView tvCarWash;
    private TextView tvSport;
    private ImageView ivBackground;
    private SwipeRefreshLayout srlRefresh;
    private String mWeatherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //背景图和状态栏的融合
        if(Build.VERSION.SDK_INT >= 21){
            View decorView =getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        swWeather = findViewById(R.id.sw_weather);
        tvTitleCity = findViewById(R.id.tv_title_city);
        tvTitleUpdatTime = findViewById(R.id.tv_title_update_time);
        tvTmp = findViewById(R.id.tv_tmp);
        tvWeatherInfo = findViewById(R.id.tv_weather_info);
        llForecast = findViewById(R.id.ll_forecast);
        tvAqi = findViewById(R.id.tv_aqi);
        tvPm25 = findViewById(R.id.tv_pm25);
        tvComfort = findViewById(R.id.tv_comfort);
        tvCarWash = findViewById(R.id.tv_car_wash);
        tvSport = findViewById(R.id.tv_sport);
        ivBackground = findViewById(R.id.iv_background);
        srlRefresh = findViewById(R.id.srl_refresh);
        tvTitleCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WeatherActivity.this,ChooseAreaActivity.class);
                startActivity(intent);
            }
        });
        final String weatherId = getIntent().getStringExtra("weather_id");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
        final String WeatherText = prefs.getString("weather"+weatherId,null);
        String backGroundPic = prefs.getString("background_pic",null);

        //下拉刷新
        srlRefresh.setColorSchemeResources(R.color.colorPrimary);
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    //question
                    getServerWeather(mWeatherId);
                    Toast.makeText(WeatherActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
                    srlRefresh.setRefreshing(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //解析天气信息
        if (WeatherText!=null){
            Weather weather = Utility.handleWeatherResponse(WeatherText);
            mWeatherId = weather.basic.weatherId;
            showWeather(weather);
        }else {
            swWeather.setVisibility(View.INVISIBLE);
            mWeatherId = weatherId;
            try {

                getServerWeather(weatherId);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (backGroundPic!=null ){
            Glide.with(WeatherActivity.this).load(backGroundPic).into(ivBackground);
        }else {
            loadBackgroundPic();
        }
    }

    public void getServerWeather(final String weatherId) throws IOException {
        HttpUtil.sendOkhttpRequest("http://guolin.tech/api/weather?cityid=" + weatherId + "&key=b99b5658611248cdbf71bf7beb06618e", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather"+weatherId, responseText);
                            editor.apply();
                            showWeather(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    public  void showWeather(Weather weather){
        tvTitleCity.setText(weather.basic.cityName);
//       tvTitleUpdatTime.setText(weather.basic.updata.updataTime.split(" ")[1]);
        tvTmp.setText(weather.now.tmp+"℃");
        tvWeatherInfo.setText(weather.now.more.info);
        tvAqi.setText(weather.aqi.city.aqi);
        tvPm25.setText(weather.aqi.city.pm25);
        tvComfort.setText(weather.suggestion.comf.info);
        tvCarWash.setText(weather.suggestion.cw.info);
        tvSport.setText(weather.suggestion.sport.info);
        llForecast.removeAllViews();
        for (Forecast forecast:weather.forecastList){
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,llForecast,false);
            TextView tvData = view.findViewById(R.id.tv_date);
            TextView tvInfo = view.findViewById(R.id.tv_info);
            TextView tvMax = view.findViewById(R.id.tv_max);
            TextView tvMin = view.findViewById(R.id.tv_min);
            tvData.setText(forecast.data);
            tvInfo.setText(forecast.more.info);
            tvMax.setText(forecast.tmp.max);
            tvMin.setText(forecast.tmp.min);
            llForecast.addView(view);
        }
        swWeather.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, AutoUpdateMyService.class);
        startService(intent);
    }

    public void loadBackgroundPic(){
        HttpUtil.sendOkhttpRequest("http://guolin.tech/api/bing_pic", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String backgroundPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("background_pic",backgroundPic);
                editor.apply();runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(backgroundPic).into(ivBackground);
                    }
                });
            }
        });
    }

    /*@Override
    public void onBackPressed() {
        Intent intent = new Intent(this,ChooseAreaActivity.class);
        startActivity(intent);
    }*/
}
