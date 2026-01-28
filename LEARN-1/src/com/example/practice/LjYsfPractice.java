package com.example.practice;

import java.util.Scanner;

public class LjYsfPractice {
    public static void main(String[] args) {

      /*
            练习1：键盘录入一个整数，判断这个数字是否在1~10之间
       */
        // 1. 键盘录入一个整数
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入一个整数：");
        int number = sc.nextInt();

        // 2. 判断这个数字是否在1~10之间
        // number > 1   number < 10 同时满足 &
        // && 是短路逻辑运算符，当左边不能确定整个表达式的结果，右边才会执行，当左边能确定整个表达式的结果，那么右边就不会执行了。从而提高了代码的运行效率。
        // && 和 || 的运算结果与 & 和 | 完全一致
        boolean result1 = number > 1 && number < 10;
        System.out.println(result1);

        // 练习2：键盘录入一个整数，判断这个数字是否不在1~10之间

        // 1. 键盘录入一个整数
//        Scanner sc = new Scanner(System.in);
        System.out.println("请输入一个整数：");
        int number2 = sc.nextInt();
        // 2. 判断这个数字是否不在1~10之间
        boolean result2 = number2 <= 1 | number2 >= 10;
        System.out.println(result2);
    }
}
