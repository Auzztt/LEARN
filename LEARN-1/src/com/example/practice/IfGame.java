package com.example.practice;

import java.util.Scanner;

public class IfGame {
    public static void main(String[] args) {
        // 1. 定义变量记录游戏人物的生命值
        int hp = 200;

        // 2. 键盘录入一个值，表示当前人物受到的伤害
        System.out.println("请输入当前人物受到的伤害：");
        Scanner sc = new Scanner(System.in);
        int hurt = sc.nextInt();

        // 3.计算当前的血量
        hp = hp - hurt;

        // 游戏人物不会死亡，最少1点血
        if(hp <= 0){
            hp = 1;
        }

        System.out.println("当前游戏人物的血量是：" + hp);

        // 4. 键盘录入一个值，表示技能回复的血量
        System.out.println("请输入技能回复的血量：");
        int add = sc.nextInt();

        // 5. 计算当前游戏人物的血量
        hp = hp + add;

        if (hp > 200){
            hp = 200;
        }

        System.out.println("当前游戏人物的血量是：" + hp);
    }
}
