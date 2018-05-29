package com.example.cn.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.cn.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends Activity {
    ViewPager viewpager=null;

    public void startMainActivity(View view){
        SharedPreferences sp= getSharedPreferences("welcome_xml",MODE_PRIVATE);
        SharedPreferences.Editor edit=sp.edit();
        edit.putBoolean("welcome",true).commit();
        //需要跳转至另外一个activity
        Intent intent=new Intent(this,MainActivity.class);
        //正常activity是一个栈结构，此处根据需求定义一个新栈，而且把之前的栈删掉
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        viewpager = (ViewPager)findViewById(R.id.vp);
        //添加引导图片，与ListView相同，需要适配器（内部已做了优化）
        //应该把图片名称获取z
        int [] resId=new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
        final List<View> iList=new ArrayList<View>();
        for (int i=0;i< resId.length;i++){
            ImageView iv=new ImageView(this);
            iv.setImageResource(resId[i]);
            iList.add(iv);
        }
        iList.add(View.inflate(WelcomeActivity.this ,R.layout.start_main,null));

        viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return iList.size();
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View iv1=(View)iList.get(position);
                container.addView(iv1);
                Log.i("zzz", "size:"+iList.size());
                return iv1;
            }
            @Override    //销毁前后隔一个
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View)object);
            }
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
        });
    }
}
