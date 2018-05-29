package com.example.cn.myapplication.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cn.myapplication.R;
import com.example.cn.myapplication.modal.Person;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

@ContentView(value=R.layout.activity_main)
public class MainActivity extends Activity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private ListView lv;
    private static final int MSG_SUCCESS = 0;// 获取图片成功的标识
    private static final int MSG_FAILURE = 1;// 获取图片失败的标识

    RadioButton rbn1=null;
    RadioButton rbn2=null;
    List<Person> prelist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);//此行代码必须添加，获取注解的值
        //setContentView(R.layout.activity_main);
        lv=(ListView)findViewById(R.id.lv);

        rbn1 = (RadioButton)findViewById(R.id.rtn1) ;
        rbn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams("http://www.jxy-edu.com/ajaxServlet");
                params.addQueryStringParameter("wd", "xUtils");
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Gson gson=new Gson();
                        Log.i("AA",result);
                        prelist= gson.fromJson(result,new TypeToken<List<Person>>() {}.getType());
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
                                Log.i("cyt","name:"+person.name+",phone:"+person.tel+",sel:"+person.salary);
                                View view=null;
                                if (convertView==null){
                                    view=View.inflate(MainActivity.this,R.layout.list_item,null);
                                }
                                else
                                {
                                    view=convertView;
                                }
                                ImageView img=(ImageView) view.findViewById(R.id.imgv);

                                try {
                                    URL url=new URL("http://img.sccnn.com/bimg/338/26585.jpg");
                                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                                    connection.setRequestMethod("GET");//设置请求方式这里的方式必须为大写
                                    connection.setConnectTimeout(5000);//设置超时的时间
                                    connection.setDoInput(true);
                                    if (connection.getResponseCode()==200)
                                    {
                                        InputStream is = connection.getInputStream();
                                        Bitmap bitmap = BitmapFactory.decodeStream(is);//写入一个bitmap流
                                        img.setImageBitmap(bitmap);
                                    }
                                    //int code = connection.getResponseCode();//获得状态码

                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (ProtocolException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                TextView tx_name=(TextView) view.findViewById(R.id.tx_name);
                                tx_name.setText(person.name.substring(0,9));
                                TextView tx_phone=(TextView) view.findViewById(R.id.tx_phone);
                                tx_phone.setText("电话："+person.tel);
                                TextView tx_sel=(TextView) view.findViewById(R.id.tx_sel);
                                tx_sel.setText("工资："+person.salary+"");
                                return view;
                            }
                        });
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        //Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
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
            }
        });

        //模拟数据读取
        //final List<Person> prelist=new ArrayList<Person>();
        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        rbn2 = (RadioButton)findViewById(R.id.rtn2) ;
        rbn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prelist!=null) {
                    prelist.clear();
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
                            return null;
                        }
                    });
                }
            }
        });




    }




}
