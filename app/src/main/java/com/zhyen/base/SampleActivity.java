package com.zhyen.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * author : fengxing
 * date : 2022/5/27 下午10:00
 * description :
 */
public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);
        findViewById(R.id.btn_button_style).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SampleActivity.this, ButtonStyleActivity.class));
            }
        });

        findViewById(R.id.btn_bottom_navigation_style).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SampleActivity.this, BottomNavStyleActivity.class));
            }
        });
    }
}
