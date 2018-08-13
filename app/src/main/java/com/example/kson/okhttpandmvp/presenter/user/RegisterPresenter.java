package com.example.kson.okhttpandmvp.presenter.user;

import android.text.TextUtils;

import com.example.kson.okhttpandmvp.bean.UserBean;
import com.example.kson.okhttpandmvp.model.user.RegisterModel;
import com.example.kson.okhttpandmvp.utils.RegexValidateUtil;
import com.example.kson.okhttpandmvp.view.IRegView;

import java.util.HashMap;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/08/07
 * Description:
 */
public class RegisterPresenter {

    private RegisterModel registerModel;
    private IRegView iRegView;

    public RegisterPresenter(IRegView iRegView) {
        onAttach(iRegView);
        this.registerModel = new RegisterModel();
    }

    /**
     * 绑定view的方法
     * @param iRegView
     */
    public void onAttach(IRegView iRegView){
        this.iRegView = iRegView;
    }

    /**
     * null
     */
    public void onDetach(){
        this.iRegView = null;
    }




    /**
     * 注册逻辑
     * @param mobile
     * @param pwd
     */
    public void register(String mobile, String pwd) {
        if (TextUtils.isEmpty(mobile)){
            iRegView.mobileEmpty();
            return;
        }

        if (!RegexValidateUtil.checkCellphone(mobile)){
            iRegView.mobileVerify();
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            iRegView.pwdVerify();
            return;
        }

//        registerModel.register(mobile,pwd);
//        registerModel.setRegCallback(new RegisterModel.RegCallback() {
//            @Override
//            public void failure(String errorMsg) {
//
//                iRegView.failure(errorMsg);
//
//            }
//
//            @Override
//            public void success(UserBean userBean) {
//
//                iRegView.success(userBean);
//
//            }
//        });

        HashMap<String,String> params = new HashMap<>();
        params.put("mobile",mobile);
        params.put("password",pwd);

        registerModel.register(params, new RegisterModel.RegCallback() {
            @Override
            public void failure(String errorMsg) {

                iRegView.failure(errorMsg);

            }

            @Override
            public void success(UserBean userBean) {

                iRegView.success(userBean);

            }
        });


    }

}
