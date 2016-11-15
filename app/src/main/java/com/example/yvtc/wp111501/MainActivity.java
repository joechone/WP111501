package com.example.yvtc.wp111501;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ArrayList<Map<String,Object>> mylist3 = new ArrayList<Map<String,Object>>();
    HashMap<String,Object> m1 =new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1.建立一個隊列
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        //2.建立一個要求
        StringRequest quest = new StringRequest("http://data.ntpc.gov.tw/od/data/api/BF90FA7E-C358-4CDA-B579-B6C84ADC96A1?$format=json",//網址
                new Response.Listener<String>() {       //成功了
                    @Override
                    public void onResponse(String s) {
                        Log.d("NET", s);
                        Gson gson = new Gson();
                        Animal[] animals = gson.fromJson(s, Animal[].class);

                        for(int i = 0 ;i< animals.length;i++)
                            {
                                m1 =new HashMap<>();

                                m1.put("district",animals[i].district);
                                m1.put("address",animals[i].address);
                                m1.put("tel",animals[i].tel);
                                m1.put("opening_hours",animals[i].opening_hours);
                                mylist3.add(m1);

                            }
                        ListView lv = (ListView) findViewById(R.id.listView);
                            SimpleAdapter adapter3 = new SimpleAdapter(MainActivity.this,
                                    mylist3,
                                    R.layout.myitem,
                                    new String[]{"district", "address", "tel"},
                                    new int[] {R.id.textView, R.id.textView2, R.id.textView3});

                            lv.setAdapter(adapter3);
//方法一
//                        try {
//                            JSONArray array = new JSONArray(s);
//                            for(int i = 0 ;i< array.length();i++)
//                            {
//                                m1 =new HashMap<>();
//                                JSONObject obj = array.getJSONObject(i);
//                                for(int k = 0 ;k< obj.length();k++)
//                                {
//                                    Log.d("district"+k, obj.getString("district"));
//                                    Log.d("address"+k, obj.getString("address"));
//                                    Log.d("tel"+k, obj.getString("tel"));
//                                    Log.d("opening_hours"+k, obj.getString("opening_hours"));
//                                }
//                                m1.put("district",obj.getString("district"));
//                                m1.put("address",obj.getString("address"));
//                                m1.put("tel",obj.getString("tel"));
//                                m1.put("opening_hours",obj.getString("opening_hours"));
//                                mylist3.add(m1);
//
//                            }
//                            ListView lv = (ListView) findViewById(R.id.listView);
//                            SimpleAdapter adapter3 = new SimpleAdapter(MainActivity.this,
//                                    mylist3,
//                                    R.layout.myitem,
//                                    new String[]{"district", "address"},
//                                    new int[] {R.id.textView, R.id.textView2});
//
//                            lv.setAdapter(adapter3);
//                        }catch (JSONException e){
//                            e.printStackTrace();
//                            Log.d("tJSONException", e.toString());
//                        }
                    }
                },
                new Response.ErrorListener() {   //失敗了
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("NET", "Error!!");
                    }
                });

        queue.add(quest);//3.把要求加入隊列
        queue.start();   //隊列啟動
    }
}
