package com.flyingwings.cn.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WaveAnimView viewById = (WaveAnimView) findViewById(R.id.wave_anim);
        viewById.animStart();

    }
}
