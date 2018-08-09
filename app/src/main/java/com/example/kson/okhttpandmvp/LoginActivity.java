package com.example.kson.okhttpandmvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kson.okhttpandmvp.bean.LoginBean;
import com.example.kson.okhttpandmvp.presenter.user.LoginPresenter;
import com.example.kson.okhttpandmvp.view.user.ILoginView;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements ILoginView{

    private EditText mobileEt,pwdEt;

    private LoginPresenter loginPresenter;
    private int REQUEST_CODE = 1;//二维码页面返回的数据请求码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    private void initView() {
        mobileEt = findViewById(R.id.mobile_et);
        pwdEt = findViewById(R.id.pwd_et);
    }

    private void initData() {
        loginPresenter = new LoginPresenter(this);
    }


    /**
     * 登录
     * @param view
     */
    public void login(View view) {
        HashMap<String,String> params = new HashMap<>();
        params.put("mobile",mobileEt.getText().toString());
        params.put("password",pwdEt.getText().toString());
        loginPresenter.login(params);

    }

    @Override
    public void mobileVerify() {

    }

    @Override
    public void mobileEmpty() {

    }

    @Override
    public void pwdVerify() {

    }

    @Override
    public void success(LoginBean userBean) {

        startActivity(new Intent(this,XRecyclerViewActivity.class));

    }

    @Override
    public void failure(String msg) {

    }

    /**
     * 二维码扫描
     * @param view
     */
    public void scan(View view) {
        Intent intent = new Intent(LoginActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this,  "解析结果:" + result, Toast.LENGTH_SHORT).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(LoginActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
