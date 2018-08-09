package com.example.kson.okhttpandmvp.model.user;

import android.os.Handler;

import com.example.kson.okhttpandmvp.bean.LoginBean;
import com.example.kson.okhttpandmvp.bean.UserBean;
import com.example.kson.okhttpandmvp.common.Api;
import com.example.kson.okhttpandmvp.utils.OkHttpUtils;
import com.example.kson.okhttpandmvp.utils.RequestCallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/08/09
 * Description:
 */
public class LoginModel {

    private Handler handler = new Handler();

    /**
     * 登录接口请求
     *
     * @param params
     */
    public void login(HashMap<String, String> params, final LoginCallback loginCallback) {

        OkHttpUtils.getInstance().postData(Api.LOGIN_URL, params, new RequestCallback() {
            @Override
            public void failure(Call call, IOException e) {
                if (loginCallback != null) {
                    loginCallback.failure("请求失败");
                }

            }

            @Override
            public void onResponse(Call call, Response response) {
                if (response.code() == 200) {
                    String result = null;//数据格式json串
                    try {
                        result = response.body().string();
                        parseJsonResult(result, loginCallback);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    /**
     *
     * @param result
     * @param loginCallback
     */
    private void parseJsonResult(String result, final LoginCallback loginCallback) {
        if (result != null) {
            final LoginBean userBean = new Gson().fromJson(result, LoginBean.class);

            handler.post(new Runnable() {//切换线程
                @Override
                public void run() {
                    //运行在主线程
                    if (loginCallback != null) {
                        loginCallback.loginSuccess(userBean);
                    }
                }
            });


        }

    }

    public interface LoginCallback {
        void failure(String errorMsg);//网络请求失败

        void loginSuccess(LoginBean userBean);
    }
}
