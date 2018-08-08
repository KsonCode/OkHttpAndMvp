package com.example.kson.okhttpandmvp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kson.okhttpandmvp.R;
import com.example.kson.okhttpandmvp.bean.ProductBean;

import java.util.List;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/08/08
 * Description:
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{

    private Context context;
    private List<ProductBean.Product> list;

    public ProductAdapter(Context context, List<ProductBean.Product> list){

        this.context = context;
        this.list = list;

    }
    /**
     *  创建viewholder 和渲染布局
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_item_layout,parent,false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }



    /**
     * 绑定viewholder，作用：展示数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductBean.Product product = list.get(position);
        //with 绑定上下文，load加载网络资源（url），into：把bitmap设置给当前控件
        String[] imageUrls = product.images.split("\\|");

        if (imageUrls!=null&&imageUrls.length>0){

            Glide.with(context).load(imageUrls[0]).into(holder.iv);//
        }

        holder.tv.setText(product.title);
    }


    @Override
    public int getItemCount() {
        return list.size()==0?0:list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv;
        private TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
