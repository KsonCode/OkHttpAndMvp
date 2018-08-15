package com.example.kson.okhttpandmvp.model.user;

import android.os.Handler;
import android.os.Message;

import com.example.kson.okhttpandmvp.bean.UserBean;
import com.example.kson.okhttpandmvp.common.Api;
import com.example.kson.okhttpandmvp.common.Constants;
import com.example.kson.okhttpandmvp.utils.OkHttpUtils;
import com.example.kson.okhttpandmvp.utils.RequestCallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/08/07
 * Description:
 */
public class RegisterModel {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private RegCallback regCallback;

    public void setRegCallback(RegCallback regCallback) {
        this.regCallback = regCallback;
    }

    /**
     * 请求网络数据，请求注册接口
     *
     * @param mobile
     * @param pwd
     */
    public void register(String mobile, String pwd) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        FormBody formBody = new FormBody.Builder()
                .add("mobile", mobile)
                .add("password", pwd).build();

        Request request = new Request.Builder()
                .url(Api.REG_URL).post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            //请求失败
            @Override
            public void onFailure(Call call, IOException e) {
                if (regCallback != null) {
                    regCallback.failure("请求失败");
                }
            }

            //请求成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Thread.currentThread().getName();
                //1.得到响应体（json串），并解析成bean类
                //2.切换线程，把bean类回调给presenter层
                if (response.code() == 200) {
                    String result = response.body().string();//数据格式json串
                    parseJsonResult(result, regCallback);
                }

            }
        });

    }
    /**
     * 请求网络数据，请求注册接口
     *
     * @param
     * @param
     */
    public void register(HashMap<String,String> params, final RegCallback regCallback) {

        OkHttpUtils.getInstance().postData( Api.REG_URL,params, new RequestCallback() {
            @Override
            public void failure(Call call, IOException e) {
                if (regCallback != null) {
                    regCallback.failure("请求失败");
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                //1.得到响应体（json串），并解析成bean类
                //2.切换线程，把bean类回调给presenter层
                if (response.code() == 200) {
                    String result = null;//数据格式json串
//                    try {
//                        result = response.body().string();
//                        parseJsonResult(result, regCallback);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                }
            }
        });

//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .build();
//
//        FormBody formBody = new FormBody.Builder()
//                .add("mobile", mobile)
//                .add("password", pwd).build();
//
//        Request request = new Request.Builder()
//                .url(Api.REG_URL).post(formBody).build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            //请求失败
//            @Override
//            public void onFailure(Call call, IOException e) {
//                if (regCallback != null) {
//                    regCallback.failure("请求失败");
//                }
//            }
//
//            //请求成功
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Thread.currentThread().getName();
//                //1.得到响应体（json串），并解析成bean类
//                //2.切换线程，把bean类回调给presenter层
//                if (response.code() == 200) {
//                    String result = response.body().string();//数据格式json串
//                    parseJsonResult(result, regCallback);
//                }
//
//            }
//        });

    }

    /**
     * 解析json为bean对象
     *
     * @param result
     */
    private void parseJsonResult(String result, final RegCallback callback) {

        if (result != null) {
            final UserBean userBean = new Gson().fromJson(result, UserBean.class);

            handler.post(new Runnable() {//切换线程
                @Override
                public void run() {
                    //运行在主线程
                    if (callback != null) {
                        callback.success(userBean);
                    }
                }
            });


        }
    }

    /**
     * 创建接口类
     */
    public interface RegCallback {
        void failure(String errorMsg);

        void success(UserBean userBean);
    }
}
