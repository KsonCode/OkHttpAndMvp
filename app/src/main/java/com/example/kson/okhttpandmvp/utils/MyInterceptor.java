package com.example.kson.okhttpandmvp.utils;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/08/15
 * Description:
 */
public class MyInterceptor implements Interceptor{
    /**
     * 拦截的方法
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {

        //1.得到原始的request对象

        Request request = chain.request();//原始的request

        Log.e("httpurl:",request.url()+"");

        //2.返回原始的response对象
        Response response = chain.proceed(request);

        Response response1 = response.newBuilder().build();

        Log.e("jsonResult:",response1.body().string());
        return response;
    }
}
