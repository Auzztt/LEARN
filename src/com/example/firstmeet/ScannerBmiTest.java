package com.example.firstmeet;

import java.util.Scanner;

public class ScannerBmiTest {
    public static void main(String[] args) {

        // BMI = 体重 / 身高的平方

        Scanner sc = new Scanner(System.in);
        // 1. 键盘录入体重 KG
        System.out.println("请输入您的体重（单位KG）：");
        double weight = sc.nextDouble();

        // 2. 键盘录入身高
        System.out.println("请输入您的身高（单位M）：");
        double height = sc.nextDouble();

        // 3. 计算BMI
        double bmi = weight / (height * height);
        System.out.println("您的BMI为：");
        System.out.println(bmi);
    }
}
