package com.example.cn.myapplication.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cn.myapplication.R;
import com.example.cn.myapplication.activity.DetailActivity;
import com.example.cn.myapplication.activity.MainActivity;
import com.example.cn.myapplication.modal.Person;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.image.SmartImageView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class ffirst extends Fragment {
    List<Person> persons =new ArrayList<Person>();
    ListView lv=null;
    public ffirst() {
        // Required empty public constructor
    }
    public class ItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String text = (String) ((TextView)view.findViewById(R.id.bh)).getText();
            //大多数情况下，position和id相同，并且都从0开始
            String showText = "点击了内码为：" + text +"的内容。" ;
            //Toast.makeText(getActivity(),showText,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("ID",text);
            startActivity(intent);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_ffirst,container,false);
        lv=(ListView)view.findViewById(R.id.lv) ;
        lv.setOnItemClickListener(new ItemClickListener());
        RequestParams params = new RequestParams("http://www.jxy-edu.com/ajaxServlet");
        params.addQueryStringParameter("wd", "xUtils");
        x.http().get(params, new Callback.CommonCallback<String>(){
                    @Override
                    public void onSuccess(String result) {
                        Gson gson=new Gson();
                        persons=gson.fromJson(result,new TypeToken<List<Person>>(){}.getType());
                        lv.setAdapter(new BaseAdapter() {
                            @Override
                            public int getCount() {
                                return persons.size();
                            }
                            @Override
                            public Object getItem(int position) {
                                return persons.get(position);
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
                                Person person=persons.get(position);
                                View v=null;
                                if (convertView==null){
                                    //view=View.inflate(null,R.layout.list_item,null);
                                    v=inflater.inflate(R.layout.list_item,container,false);

                                }
                                else
                                {
                                    v=convertView;
                                }
                                SmartImageView img=(SmartImageView) v.findViewById(R.id.imgv);
                                img.setImageUrl(person.name);

                                TextView tx_name=(TextView) v.findViewById(R.id.tx_name);
                                tx_name.setText("张三丰");
                                TextView tx_phone=(TextView) v.findViewById(R.id.tx_phone);
                                tx_phone.setText("电话："+person.tel);
                                TextView tx_sel=(TextView) v.findViewById(R.id.tx_sel);
                                tx_sel.setText("工资："+person.salary+"");
                                TextView tx_bh=(TextView) v.findViewById(R.id.bh);
                                tx_bh.setText("内码10010"+position);

                                return v;
                            }
                        });
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(x.app(), "error", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onCancelled(CancelledException cex) {
                        Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFinished() {

                    }
                });


        return view;

    }

}
