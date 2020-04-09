package com.dingxiang.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

// 游戏面板，在上面画界面，画蛇
public class GamePanel extends JPanel implements KeyListener, ActionListener {

    int length;                     // 蛇的长度
    int[] snakeX = new int[600];    // 蛇的X坐标
    int[] snakeY = new int[500];    // 蛇的Y坐标
    String direction;               // 蛇头朝向 UP:上 DOWN:下 LEFT:左 RIGHT:右
    boolean isStart = false;        // 游戏是否开始
    Timer timer = new Timer(100,this);  // 定时器

    // 定义一个食物
    int foodX;
    int foodY;
    Random random = new Random();

    // 死亡判断
    boolean isFail = false;

    // 积分系统
    int score;

    public GamePanel(){
        init();
        // 获取键盘的监听事件
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();  // 让时间动起来
    }

    public void init(){
        length = 3;
        snakeX[0] = 100; snakeY[0] = 100;   // 蛇头坐标
        snakeX[1] = 75; snakeY[1] = 100;    // 第一个身体坐标
        snakeX[2] = 50; snakeY[2] = 100;    // 第二个身体坐标
        direction = "RIGHT";

        foodX = 25 + 25 * random.nextInt(34);
        foodY = 75 + 25 * random.nextInt(24);

        score = 0;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);    //清屏
        this.setBackground(Color.WHITE);

        Data.head.paintIcon(this,g,25,11);  // 顶部广告栏
        g.fillRect(25,75,850,600);  // 绘制游戏区域

        if (direction.equals("UP")){
            Data.up.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if (direction.equals("DOWN")){
            Data.down.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if (direction.equals("LEFT")){
            Data.left.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if (direction.equals("RIGHT")){
            Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);
        }

        for (int i = 1; i < length; i++) {
            Data.body.paintIcon(this,g,snakeX[i],snakeY[i]);
        }

        // 画食物
        Data.food.paintIcon(this,g,foodX,foodY);

        // 画积分
        g.setColor(Color.WHITE);
        g.setFont(new Font("微软雅黑",Font.BOLD,18));
        g.drawString("长度：" + length,750,35);
        g.drawString("分数：" + score,750,50);

        // 游戏提示：是否开始
        if (isStart == false){
            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("按下空格键开始游戏",300,300);
        }

        // 失败提醒
        if (isFail){
            g.setColor(Color.RED);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("游戏结束，按下空格键重新开始",200,300);
        }
    }

    // 监听接收键盘的输入
    @Override
    public void keyPressed(KeyEvent e) {
        // 获取按下的是键盘上的哪个键
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE){  // 如果是空格键
            if (isFail){
                isFail = false;
                init();
            }else {
                isStart = !isStart;
            }
            repaint();  // 刷新界面
        }

        // 键盘控制走向
        if (keyCode == KeyEvent.VK_UP){
            direction = "UP";
        }else if (keyCode == KeyEvent.VK_DOWN){
            direction = "DOWN";
        }else if (keyCode == KeyEvent.VK_LEFT){
            direction = "LEFT";
        }else if (keyCode == KeyEvent.VK_RIGHT){
            direction = "RIGHT";
        }
    }

    // 执行定时操作
    @Override
    public void actionPerformed(ActionEvent e) {
        // 如果游戏处于开始状态，并且游戏没有结束
        if (isStart && !isFail){
            for (int i = length-1; i > 0; i--) {
                snakeX[i] = snakeX[i-1];
                snakeY[i] = snakeY[i-1];
            }

            // 通过控制方向让头部移动
            if (direction.equals("UP")){
                snakeY[0] = snakeY[0] - 25;
                if (snakeY[0] < 75){ snakeY[0] = 650; }   // 边界判断
            }else if(direction.equals("DOWN")){
                snakeY[0] = snakeY[0] + 25;
                if (snakeY[0] > 650){ snakeY[0] = 75; }   // 边界判断
            }else if(direction.equals("LEFT")){
                snakeX[0] = snakeX[0] - 25;
                if (snakeX[0] < 25){ snakeX[0] = 850; }   // 边界判断
            }else if(direction.equals("RIGHT")){
                snakeX[0] = snakeX[0] + 25;
                if (snakeX[0] > 850){ snakeX[0] = 25; }   // 边界判断
            }

            // 如果蛇头和食物坐标重合了
            if (snakeX[0] == foodX && snakeY[0] == foodY){
                length++;
                score += 10;
                //重新生成食物
                foodX = 25 + 25 * random.nextInt(34);
                foodY = 75 + 25 * random.nextInt(24);
            }

            for (int i = 1; i < length; i++) {
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]){
                    isFail = true;
                }
            }


            repaint();  // 刷新
        }
        timer.start();  // 让时间动起来
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
