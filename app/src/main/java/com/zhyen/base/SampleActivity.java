package com.zhyen.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
        findViewById(R.id.btn_dialog_style).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder ab = new AlertDialog.Builder(SampleActivity.this);
                ab.setCancelable(false);
                ab.setTitle("温馨提醒");
                ab.setMessage("想要跳转到Test4Activity么？(触发了\"/inter/test1\"拦截器，拦截了本次跳转)");
                ab.setNegativeButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ///callback.onContinue(postcard);
                    }
                });
                ab.setNeutralButton("算了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ///callback.onInterrupt(null);
                    }
                });
                ab.setPositiveButton("加点料", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        postcard.withString("extra", "我是在拦截器中附加的参数");
//                        callback.onContinue(postcard);
                    }
                });
                ab.create().show();
            }
        });
    }
}
