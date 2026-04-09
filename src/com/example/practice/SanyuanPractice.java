package com.example.practice;

/*
格式：关系表达式 ？ 表达式1 ：表达式2 ；
计算规则：
- 计算关系表达式的值。
- 如果关系表达式的值为真，那么执行表达式1。
- 如果关系表达式的值为假，那么执行表达式2。
 */

public class SanyuanPractice {
    public static void main(String[] args) {
        //需求：求两个数的较大值
        int a = 10;
        int b = 20;

        //格式：关系表达式 ？ 表达式1 ： 表达式2 ；
        //注意点：
        //三元运算符的最终结果一定要被使用。
        //要么赋值给一个变量，要么直接输出。
        int max =  a > b ? a : b;
        System.out.println(max);

        System.out.println(a > b ? a : b);
    }
}
