package com.example.kson.okhttpandmvp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kson.okhttpandmvp.bean.LoginBean;
import com.example.kson.okhttpandmvp.common.Api;
import com.example.kson.okhttpandmvp.presenter.user.LoginPresenter;
import com.example.kson.okhttpandmvp.utils.OkHttpUtils;
import com.example.kson.okhttpandmvp.utils.RequestCallback;
import com.example.kson.okhttpandmvp.view.user.ILoginView;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements ILoginView{

    private EditText mobileEt,pwdEt;

    private LoginPresenter loginPresenter;
    private int REQUEST_CODE = 0x1000;//二维码页面返回的数据请求码
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
        getSupportActionBar().hide();//隐藏标题栏
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

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onstart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("onrestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("stop");
    }


    /**
     * 二维码扫描
     * @param view
     */
    public void scan(View view) {
        Intent intent = new Intent(LoginActivity.this, CaptureActivity.class);
//        startActivity(intent);
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

    /**
     * 京东，上传头像
     * @param view
     */
    public void upload(View view) {
        //获取图片资源
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

            String filePath = Environment.getDownloadCacheDirectory().getAbsolutePath()+"/kson/hello.jpg";
            System.out.println("filepath："+filePath);

            File file  = new File(filePath);
            HashMap<String,Object> params = new HashMap<>();
            params.put("uid","71");
            params.put("file",file);

            OkHttpUtils.getInstance().uploadFile(Api.UPLOAD_URL, params, new RequestCallback() {
                @Override
                public void failure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) {
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             try {
                                 Toast.makeText(LoginActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
                             } catch (IOException e) {
                                 e.printStackTrace();
                             }
                         }
                     });
                }
            });





        }

    }
}
