package com.zhyen.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhyen.common.BaseActivity;

/**
 * author : fengxing
 * date : 2022/5/27 下午10:00
 * description :
 */
public class SampleActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);
        findViewById(R.id.btn_enter_ui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
