package com.example.kson.okhttpandmvp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kson.okhttpandmvp.adapter.ProductAdapter;
import com.example.kson.okhttpandmvp.bean.ProductBean;
import com.example.kson.okhttpandmvp.common.Api;
import com.example.kson.okhttpandmvp.utils.OkHttpUtils;
import com.example.kson.okhttpandmvp.utils.RequestCallback;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class XRecyclerViewActivity extends AppCompatActivity implements XRecyclerView.LoadingListener,ProductAdapter.OnItemClickListener {

    private XRecyclerView xRecyclerView;
    private Handler handler = new Handler();
    private ProductBean productBean;
    private ProductAdapter productAdapter;
    private int page = 1;//默认第一页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xrecycler_view);
        initView();
    }

    private void initView() {
        xRecyclerView = findViewById(R.id.productXRv);
        xRecyclerView.setLoadingListener(this);
        xRecyclerView.setLoadingMoreEnabled(true);//是否支持上拉加载更多

        requestProduct();



    }


    /**
     * 请求商品列表
     *
     * @param
     */
    public void requestProduct() {

        HashMap<String, String> params = new HashMap<>();
        params.put("keywords", "手机");
        params.put("page", page+"");

        OkHttpUtils.getInstance().postData(Api.PRODUCT_URL, params, new RequestCallback() {
            @Override
            public void failure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {

                String result = null;
                if (response.code() == 200) {
                    try {
                        result = response.body().string();
                        System.out.println("result:" + result);
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
     *
     * @param result
     */
    private void parseProductBean(String result) {


        productBean = new Gson().fromJson(result, ProductBean.class);

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

        System.out.println("page:"+page);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclervie 集listview，gridview和瀑布流效果的view
        if (page==1){
            productAdapter = new ProductAdapter(this, productBean.data);
            xRecyclerView.setAdapter(productAdapter);
            xRecyclerView.refreshComplete();//刷新完成
        }else{
            if (productAdapter!=null){
                productAdapter.loadData(productBean.data);
                xRecyclerView.loadMoreComplete();
            }
        }
        productAdapter.setOnItemClickListener(this);
        //设置线性列表

//        xRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL));




    }

    /**
     *下载刷新
     */
    @Override
    public void onRefresh() {

        page = 1;//第一页数据

        requestProduct();
//        xRecyclerView.refreshComplete();

    }

    /**
     *加载更多，listview优化，列表优化
     */
    @Override
    public void onLoadMore() {

        page++;

        requestProduct();



    }

    /**
     * recyclerview条目点击事件
     * @param position
     */
    @Override
    public void onItemClick(int position) {

        ProductBean.Product product = productBean.data.get(position);//当前item对应的数据
//                Intent intent = new Intent(XRecyclerViewActivity.this,ProductDetailActivity.class);
//                intent.putExtra("pid",product.pid);
//                startActivity(intent);

        Toast.makeText(XRecyclerViewActivity.this, ""+product.title, Toast.LENGTH_SHORT).show();

    }
}
