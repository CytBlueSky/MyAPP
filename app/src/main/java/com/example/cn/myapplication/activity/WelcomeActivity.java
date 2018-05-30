package com.example.cn.myapplication.activity;

import android.app.Activity;
import android.content.Context;
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
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.cn.myapplication.R;
import com.viewpagerindicator.CirclePageIndicator;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends Activity {
    ViewPager viewpager=null;
    int pointMoveWidth=80;
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



    public class  DensityUtils{
        public int dpi2px(Context ctx, int dpi){
            return (int)(ctx.getResources().getDisplayMetrics().density*dpi);
        }
        public int px2dpi(Context ctx,int px){
            return (int)(px/ctx.getResources().getDisplayMetrics().density);
        }
    }

    private  void InitData(){
        viewpager.setAdapter(new WelcomePage());

        CirclePageIndicator circleIndicator = (CirclePageIndicator)findViewById(R.id.circle);
        circleIndicator.setViewPager(viewpager);
        //circleIndicator.setOnPageChangeListener(mPageChangeListener);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        viewpager = (ViewPager)findViewById(R.id.vp);
        //添加引导图片，与ListView相同，需要适配器（内部已做了优化）
        //应该把图片名称获取z
        InitData() ;


    }



    private class  WelcomePage extends PagerAdapter{
        private int[] resId=null;
        private List<View> iList=null;

        public WelcomePage(){
            resId=new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
            iList=new ArrayList<View>();
            for (int i=0;i< resId.length;i++){
                ImageView iv=new ImageView(WelcomeActivity.this);
                iv.setImageResource(resId[i]);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iList.add(iv);
            }
            iList.add(View.inflate(WelcomeActivity.this ,R.layout.start_main,null));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View iv1=(View)iList.get(position);
            container.addView(iv1);
            Log.i("zzz", "size:"+iList.size());
            return iv1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return iList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
