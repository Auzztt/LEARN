package com.example.practice;

import java.util.Scanner;

public class ScannerVariableCal {
    public static void main(String[] args) {
        /*时间拆分，给定一个秒数，拆分成x小时y分钟z秒的格式*/
        // 1. 输入变量记录秒数
        Scanner sc = new Scanner(System.in);
        int seconds = sc.nextInt();
        // 2. 获取小时数
        int hours = seconds / 3600; //3661 / 3600 = 1 .... 61
        System.out.println(hours);
        // 3. 获取分钟数
        //    总秒数 - 小时数 3661 - 3600 = 61
        //    61 / 60 = 1分钟 ... 1秒钟
        //  3661 % 3600 = 1 ... 61
        //  7220 % 3600 = 2 ... 20
        int min = seconds % 3600 / 60;
        System.out.println(min);
        // 4. 获取秒数
        int second = seconds % 3600 % 60;
        System.out.println(second);
        // 字符串拼接的形式打印数据
        // 2小时0分42秒
        System.out.println(hours + "小时" + min + "分" + second + "秒");
    }
}
