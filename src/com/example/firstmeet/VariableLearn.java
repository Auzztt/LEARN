package com.example.firstmeet;

import java.util.Scanner;

public class VariableLearn {
    public static void main(String[] args){
        /* 微信余额：100元
        支付宝余额：10元
        银行卡余额：20元
        问题一：请问现在一共有多少钱？
        问题二：微信收了10元红包，又发了2元红包，余额多少？*/
        // 1. 定义一个变量记录微信的余额
        // 整数：int
        // 小数：（浮点数）double
        double a = 100;

        // 2. 定义一个变量记录支付宝的余额
        double b = 10;

        // 3. 定义一个变量记录银行卡的余额
        double c = 20;

        // 4. 输出现在总共有多少钱?
        System.out.println(a + b + c);

        // 5. 微信收了10元红包
        // 原来的余额 + 10元红包 = 新的余额
        // 让变量a记录新的余额

        // 执行过程：
        //  1. 计算 a + 10 ，此时变量a里面记录的是100， 100 + 10，得到结果110
        //  2. 再把110赋值给变量a，此时变量a原本记录的值，就被覆盖了
        a = a + 10;
        a = a - 2 ;
        System.out.println(a);

        /*
            算数运算符：+ - * / %

            整数计算、小数计算
        */
        // 1. 整数计算
        // 细节：整数相除结果还是整数，就是商
        //        % 取模，取余
        //       其他运算跟数学中是一模一样的
        // 2. 小数计算
        //       小数直接参与计算，结果有可能不精确
        double d = 1.1;
        double e = 1.01;

        System.out.println(d + e);
        System.out.println(d - e);
        System.out.println(d * e);
        System.out.println(d / e);
        System.out.println(d % e);

        //可以利用取模来判断一个数是奇数还是偶数
        System.out.println(15 % 2); //1  结果非0就是奇数

        // 数值拆分练习
        //1.键盘录入一个三位数
        //导包 --- 创建对象 --- 接收数据
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入一个三位整数");
        int number = sc.nextInt(); //123

        //2.获取这个三位数的个位、十位、百位并打印出来
        //公式：
        //针对于任意的一个数而言
        //个位： 数字 % 10
        int ones = number % 10;
        //十位： 数字 / 10 % 10
        int tens = number / 10 % 10;
        //百位： 数字 / 100 % 10
        int hundreds = number / 100  % 10;

        //输出结果
        System.out.println("该三位数的个位、十位、百位分别为：");
        System.out.println(ones);
        System.out.println(tens);
        System.out.println(hundreds);
    }
}
