package com.example.kson.okhttpandmvp.presenter.user;

import com.example.kson.okhttpandmvp.bean.LoginBean;
import com.example.kson.okhttpandmvp.bean.UserBean;
import com.example.kson.okhttpandmvp.model.user.LoginModel;
import com.example.kson.okhttpandmvp.view.user.ILoginView;

import java.util.HashMap;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/08/09
 * Description:
 */
public class LoginPresenter {
    private LoginModel loginModel;
    private ILoginView iLoginView;

    public LoginPresenter(ILoginView iLoginView) {
        this.loginModel = new LoginModel();
        this.iLoginView = iLoginView;
    }

    public void login(HashMap<String, String> params) {

        loginModel.login(params, new LoginModel.LoginCallback() {
            @Override
            public void failure(String errorMsg) {
                iLoginView.failure(errorMsg);
            }

            @Override
            public void loginSuccess(LoginBean userBean) {
                iLoginView.success(userBean);
            }
        });

    }
}
