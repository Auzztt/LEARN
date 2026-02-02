package com.example.processcontrol;

public class IfPractice {
    public static void main(String[] args) {
        //定义两个变量
        int a = 10;
        int b = 20;
        // 单if语句
        System.out.println("示例一开始");
        //需求：判断a和b的值是否相等，如果相等，就在控制台输出：a等于b
        if(a == b) {
            System.out.println("a等于b");
        }    //需求：判断a和c的值是否相等，如果相等，就在控制台输出：a等于c
        int c = 10;
        if(a == c) {
            System.out.println("a等于c");
        }
        System.out.println("示例一结束");

        // if + else 语句
        System.out.println("示例二开始");
        if(a > b) {
            System.out.println("a的值大于b");
        } else {
            System.out.println("a的值不大于b");
        }
        System.out.println("示例二结束");

        // if + else if + else 语句
//        if (关系表达式1) {
//            语句体1;
//        } else if (关系表达式2) {
//            语句体2;
//        }
//      …
//      else {
//            语句体n+1;
//        }

        // tips:1.只要有if就跟着表达式判断  2.如果if后的大括号中语句体只有一行，大括号可以省略
    }
}
