package com.example.practice;

public class Bmi {
    public static void main(String[] args) {
        // BMI = 体重 / 身高的平方

        // 1. 定义变量记录我的体重 68.9KG
        double weight = 50.3;

        // 2. 定义变量记录我的身高
        double height = 1.72;

        // 3. 计算BMI
        double bmi = weight / (height * height);
        System.out.println(bmi);
    }
}
