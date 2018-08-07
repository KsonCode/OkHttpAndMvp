package com.example.kson.okhttpandmvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.kson.okhttpandmvp.common.Api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//
//        Handler handler = new Handler();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });

        //feature


    }



    /**
     * 登录接口
     * @param view
     */
    public void login(View view) {

        System.out.println("oncreate:"+Thread.currentThread().getName());



        HashMap<String,String> params = new HashMap<>();
        params.put("mobile","18612991023");
        params.put("password","222222");

        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
            sb.append(stringStringEntry.getKey()).append("=").append(stringStringEntry.getValue()).append("&");

        }
        System.out.println("sbsbsb:"+sb.toString());
        //1.全局管理类
        //java设计模式：build模式，构建者模式：复杂对象不断构建的过程
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .writeTimeout(5000,TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .build();

        //构建请求对象
        final Request request = new Request.Builder()
                .url(Api.LOGIN_URL+"?"+sb.toString())
                .get()
                .addHeader("source","android")
                .build();



        //创建真正的请求对象
        //1.同步请求

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Call call = okHttpClient.newCall(request);
//                    Response response = call.execute();
//                    if (response.code()==200){
//                        String result = response.body().string();
//                        System.out.println("result:"+result);
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

//        异步请求

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                System.out.println("onResponse==========="+Thread.currentThread().getName());


//                System.out.println("response:"+response.body().string());
//                Toast.makeText(MainActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
            }
        });




    }

    /**
     * post请求方式
     * @param view
     */
    public void postLogin(View view) {

        HashMap<String,String> params = new HashMap<>();
        params.put("mobile","18612991023");
        params.put("password","222222");

        //1.全局管理类
        //java设计模式：build模式，构建者模式：复杂对象不断构建的过程
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .writeTimeout(5000,TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .build();

        FormBody formBody = new FormBody.Builder()
                .add("mobile","18612991023")
                .add("password","222222")
                .build();

        //构建请求对象
        final Request request = new Request.Builder()
                .url(Api.LOGIN_URL)
                .post(formBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("postresponse========="+response.body().string());
            }
        });
    }
}
