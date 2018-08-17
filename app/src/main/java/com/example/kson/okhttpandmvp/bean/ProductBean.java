package com.example.kson.okhttpandmvp.bean;

import java.util.List;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/08/08
 * Description:
 */
public class ProductBean {
    public String msg;
    public String code;
    public List<Product> data;

    public static class Product{
        public String title;
        public String images;
        public String pid;

    }

}
