package com.example.cn.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;

import com.example.cn.myapplication.modal.Person;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=(ListView)findViewById(R.id.lv);
        //模拟数据读取
        final List<Person> prelist=new ArrayList<Person>();
        for (int i=0;i<10;i++){
            prelist.add(new Person("姓名"+i,"12345678912",10*i+1));
        }
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return prelist.size();
            }
            @Override
            public Object getItem(int position) {
                return prelist.get(position);
            }
            @Override
            public long getItemId(int position) {
                return 0;
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                //获取布局文件
                //添加数据
                //返回View组件
                Person person=prelist.get(position);
                Log.i("zp","position"+position+","+convertView+",person:"+person);
                View view=null;
                if (convertView==null){
                    view=View.inflate(MainActivity.this,R.layout.list_item,null);
                }
                else
                {
                    view=convertView;
                }
                TextView tx_name=(TextView) view.findViewById(R.id.tx_name);
                tx_name.setText(person.name);
                TextView tx_phone=(TextView) view.findViewById(R.id.tx_phone);
                tx_phone.setText(person.phone);
                TextView tx_sel=(TextView) view.findViewById(R.id.tx_sel);
                tx_sel.setText(person.sel+"");
                return view;
            }
        });
    }


}
