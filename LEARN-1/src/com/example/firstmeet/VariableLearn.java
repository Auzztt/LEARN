package com.example.firstmeet;

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
    }
}
