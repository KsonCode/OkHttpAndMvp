package com.example.kson.okhttpandmvp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context context;
    private List<ProductBean.Product> list;
    private OnItemClickListener onItemClickListener;

    public ProductAdapter(Context context, List<ProductBean.Product> list) {

        this.context = context;
        this.list = list;

    }

    public void add(ProductBean.Product product){
        this.list.add(0,product);
//        notifyDataSetChanged();//全部刷新
        notifyItemInserted(0);
    }

    /**
     * 创建viewholder 和渲染布局
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_item_layout, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }


    /**
     * 绑定viewholder，作用：展示数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        ProductBean.Product product = list.get(position);
        //with 绑定上下文，load加载网络资源（url），into：把bitmap设置给当前控件
        String[] imageUrls = product.images.split("\\|");

        if (imageUrls != null && imageUrls.length > 0) {

            Glide.with(context).load(imageUrls[0]).into(holder.iv);//
        }

        holder.tv.setText(product.title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    public void loadData(List<ProductBean.Product> data) {

        if (this.list!=null){
            this.list.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void delete() {
        this.list.remove(0);
        notifyItemRemoved(0);//局部删除
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
