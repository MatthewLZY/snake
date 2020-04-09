package com.dingxiang.snake;

import javax.swing.*;

public class StartGame {
    public static void main(String[] args) {
        // 1. 绘制一个静态窗口，用来展示游戏界面
        JFrame frame = new JFrame("贪吃蛇小游戏-墨洛潇出品");
        frame.setBounds(100,10,900,720);   // 设置窗体大小
        frame.setResizable(false);  // 固定窗体大小
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // 设置可关闭

        // 2. 加入面板
        frame.add(new GamePanel());



        frame.setVisible(true); // 设置窗体可见

    }
}
