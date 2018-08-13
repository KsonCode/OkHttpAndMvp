package com.example.kson.okhttpandmvp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kson.okhttpandmvp.bean.UserBean;
import com.example.kson.okhttpandmvp.presenter.user.RegisterPresenter;
import com.example.kson.okhttpandmvp.view.IRegView;

public class RegisterActivity extends AppCompatActivity implements IRegView {

    private EditText mobileEt, pwdEt;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initData();
    }

    private void initView() {
        mobileEt = findViewById(R.id.mobile_et);
        pwdEt = findViewById(R.id.pwd_et);

    }

    private void initData() {
        presenter = new RegisterPresenter(this);
    }

    /**
     * 注册
     *
     * @param view
     */
    public void register(View view) {
        presenter.register(mobileEt.getText().toString(), pwdEt.getText().toString());
    }

    @Override
    public void mobileVerify() {

        Toast.makeText(this, "手机号必须11位数的合法手机号", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void mobileEmpty() {
        Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void pwdVerify() {

        Toast.makeText(this, "密码不合法", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void success(UserBean userBean) {

        Toast.makeText(this, userBean.msg, Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, LoginActivity.class));

    }

    @Override
    public void failure(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){

            presenter.onDetach();
        }
    }
}
