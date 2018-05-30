package com.example.cn.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.example.cn.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

public class IndexActivity extends Activity {
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK)
        {
            exitApp();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);


    }

    @Override
    protected void onResume() {
        super.onResume();
        //首页2秒后进行重定向
        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                //判断用户是否已经查看过引导页
                SharedPreferences sp= getSharedPreferences("welcome_xml",MODE_PRIVATE);
                boolean flag=sp.getBoolean("welcome",false);
                if (flag)
                {
                    Intent intent=new Intent(IndexActivity.this,MainActivity.class);
                    //正常activity是一个栈结构，此处根据需求定义一个新栈，而且把之前的栈删掉
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else{
                    Intent intent=new Intent(IndexActivity.this,WelcomeActivity.class);
                    startActivity(intent);
                }
            }
        },2000);
    }
}
