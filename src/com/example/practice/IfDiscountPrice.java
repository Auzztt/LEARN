package com.example.practice;
/*
假设，现在有以下优惠券
     全场商品满10减8
     全场商品满50减30
     全场商品满100减50
     全场商品满200减90

     会员卡：全场8折
 请问：会员卡和优惠券不能同时使用，最优惠的价格是多少？
 */
public class IfDiscountPrice {
    public static void main(String[] args) {
        // 1. 定义变量记录商品的价格
        double price = 1000;
        // 2. 定义变量记录使用优惠券之后的价格
        double discountPrice = 0;

        // 3. 计算使用优惠券之后的价格
        if (price > 0) {
            if (price <= 10) {
                discountPrice = price;
            } else if (price <= 50) {
                discountPrice = price - 8;
            } else if (price <= 100) {
                discountPrice = price - 30;
            } else if (price <= 200) {
                discountPrice = price - 50;
            } else {
                discountPrice = price - 90;
            }
        } else {
            System.out.println("商品价格有误");
        }

        // 4. 定义变量记录使用会员卡之后的价格
        double memberPrice = price * 0.8;

        // 5. 比较优惠券的价格和会员卡的价格
        if (discountPrice < memberPrice) {
            System.out.println("直接使用代金券即可：" + discountPrice);
        } else {
            System.out.println("会员卡打折更优惠：" + memberPrice);
        }
    }
}
