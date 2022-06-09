package com.zhyen.common;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * author : fengxing
 * date : 2022/5/23 下午3:03
 * description :
 */
public class BaseActivity extends AppCompatActivity {

    protected static final String TAG = BaseActivity.class.getSimpleName();
    protected boolean isThemeDefault = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 2022/5/27 主题动态切换
        setTheme(isThemeDefault ? com.map.base.ui.R.style.AppTheme : com.map.base.ui.R.style.AppDarkTheme);
    }
}
