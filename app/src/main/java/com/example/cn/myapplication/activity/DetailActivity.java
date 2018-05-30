package com.example.cn.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cn.myapplication.R;

public class DetailActivity extends AppCompatActivity {
    TextView txt=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        txt=(TextView)findViewById(R.id.txt);
        txt.setText(getIntent().getStringExtra("ID").toString());
    }
}
