package com.example.practice;

import java.util.Scanner;

public class IfWaiMai {
    public static void main(String[] args) {
        // 1. 定义一个变量记录价格
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入订单的价格：");
        double price = sc.nextDouble();

        // 2. 计算两个APP优惠之后的价格
        double price1 = price * 0.9; // 饱了么App 打九折

        System.out.println(price1);

        // 美单App 满30-10
        double price2 = 0;
        if(price >= 30){
            // 满30减10元
            price2 = price - 10;
        }else{
            // 原价
            price2 = price;
        }

        System.out.println(price2);

        // 3. 判断
        if(price1 < price2){
            System.out.println("饱了么App最便宜");
        }else{
            System.out.println("美单App最便宜");
        }
    }
}
