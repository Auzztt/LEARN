package com.example.processcontrol;

import java.util.Scanner;

public class LoopandBreak {
    public static void main(String[] args) {
        // 1. 定义变量记录游戏人物的生命值
        int hp = 200;

        // 2. 键盘录入一个值，表示当前人物受到的伤害

        Scanner sc = new Scanner(System.in);
        // 循环 + break
        // 一直录入数据，直到数据符合要求为止
        int hurt = 0;
        while (true){
            System.out.println("请输入当前人物受到的伤害：");
            hurt = sc.nextInt();
            // 判断是否符合当前的需求
            if (hurt > 0) {
                break;
            }else{
                System.out.println("收到的伤害不能为负数");
            }
        }

        // 3.计算当前的血量
        hp = hp - hurt;

        // 游戏人物不会死亡，最少1点血
        if (hp <= 0) {
            hp = 1;
        }

        System.out.println("当前游戏人物的血量是：" + hp);

        // 4. 键盘录入一个值，表示技能回复的血量
        int add = 0;
        while (true){
            System.out.println("请输入技能回复的血量：");
            add = sc.nextInt();
            if(add > 0){
                break;
            }else{
                System.out.println("恢复的血量不能为负数");
            }
        }
        // 5. 计算当前游戏人物的血量
        hp = hp + add;

        if (hp > 200) {
            hp = 200;
        }

        System.out.println("当前游戏人物的血量是：" + hp);

    }
}
