package com.example.kson.okhttpandmvp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.kson.okhttpandmvp.adapter.ProductAdapter;
import com.example.kson.okhttpandmvp.bean.ProductBean;
import com.example.kson.okhttpandmvp.common.Api;
import com.example.kson.okhttpandmvp.utils.OkHttpUtils;
import com.example.kson.okhttpandmvp.utils.RequestCallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Handler handler = new Handler();
    private ProductBean productBean;
    private ProductAdapter productAdapter;
    private List<ProductBean.Product> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initView();
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
    }

    private void initView() {
        recyclerView = findViewById(R.id.productRv);
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.HORIZONTAL));

    }

    /**
     * 请求商品列表
     * @param view
     */
    public void requestProduct(View view) {

        HashMap<String,String> params = new HashMap<>();
        params.put("keywords","手机");

        OkHttpUtils.getInstance().getData(Api.PRODUCT_URL,params , new RequestCallback() {
            @Override
            public void failure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {

                String result = null;
                if (response.code()==200){
                    try {
                        result = response.body().string();
                        System.out.println("result:"+result);
                        parseProductBean(result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    /**
     * 解析
     * @param result
     */
    private void parseProductBean(String result) {


        productBean = new Gson().fromJson(result,ProductBean.class);

        handler.post(new Runnable() {
            @Override
            public void run() {
                fillDatas();
            }
        });
    }

    /**
     * 绘制列表,使用recyclerview
     */
    private void fillDatas() {
        list = productBean.data;
        //recyclervie 集listview，gridview和瀑布流效果的view
        productAdapter = new ProductAdapter(this,list);
        //设置线性列表
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(productAdapter);

    }

    /**
     * 动态添加数据
     * @param view
     */
    public void add(View view) {
        ProductBean.Product product = new ProductBean.Product();
        product.title = "我添加的商品";
        product.images= "|||";

        if (productAdapter!=null){
            productAdapter.add(product);
        }

    }

    public void delete(View view) {
        if (productAdapter!=null){
            productAdapter.delete();
        }
    }
}
