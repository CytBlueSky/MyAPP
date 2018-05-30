package com.example.cn.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cn.myapplication.R;
import com.example.cn.myapplication.fragment.ffirst;
import com.example.cn.myapplication.fragment.ffourth;
import com.example.cn.myapplication.fragment.fsecond;
import com.example.cn.myapplication.fragment.fthird;
import com.example.cn.myapplication.modal.Person;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.image.SmartImageView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@ContentView(value=R.layout.activity_main)
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    RadioButton rbn1=null;
    RadioButton rbn2=null;
    RadioButton rbn3=null;
    RadioButton rbn4=null;
    protected FrameLayout mFrame;
    FragmentManager fm;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK)
        {
            exitApp();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private boolean isExit=false;
    private void exitApp(){
        if (!isExit) {
            isExit = true;
            //启动一个线程，2秒后修改为false
            Toast.makeText(this, "再按一次退出APP", Toast.LENGTH_LONG).show();
            new Timer(true).schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit=false;
                }
            },2000);
        }
        else{
            //调转至主页面
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);//此行代码必须添加，获取注解的值
        //setContentView(R.layout.activity_main)
        rbn1=(RadioButton)findViewById(R.id.rtn1);
        rbn2=(RadioButton)findViewById(R.id.rtn2);
        rbn3=(RadioButton)findViewById(R.id.rtn3);
        rbn4=(RadioButton)findViewById(R.id.rtn4);
        rbn1.setOnClickListener(this);
        rbn2.setOnClickListener(this);
        rbn3.setOnClickListener(this);
        rbn4.setOnClickListener(this);
        fm= getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.mFrame,new fsecond()).commit();
        rbn1.setChecked(true);
    }
    @Override
    public void onClick(View v) {
        switch ((String)v.getTag()){
            case "rtn1":
                fm.beginTransaction().replace(R.id.mFrame,new fsecond()).commit();
                // FragmentManager工具类,来获取Fragment
                break;
            case "rtn2":
                FragmentTransaction ft= fm.beginTransaction();
                ft.replace(R.id.mFrame,new ffirst());
                ft.commit();
                break;
            case "rtn3":
                fm.beginTransaction().replace(R.id.mFrame,new fthird()).commit();
                break;
            case "rtn4":
                fm.beginTransaction().replace(R.id.mFrame,new ffourth()).commit();
                break;
        }
    }
}
