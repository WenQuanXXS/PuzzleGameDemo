package ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {

    int[][] data = new int[4][4];
    int x, y;
    int step = 0;
    JMenuItem restartItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭");

    JMenuItem accountItem = new JMenuItem("个人账号");

    public GameJFrame() {
        initJFrame();

        initJMenuBar();

        initData();

        initImage();

        //显示界面
        this.setVisible(true);
    }

    private void initData() {
        step = 0;
        int[] tempArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

        //打乱内部数据
        for (int i = 0; i < tempArr.length; i++) {
            Random r = new Random();
            int tempIndex = r.nextInt(tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[tempIndex];
            tempArr[tempIndex] = temp;
        }

        //把tempArr的值赋值给data
        for (int i = 0; i < tempArr.length; i++) {
            //创造空图片的原理为文件夹内没有piece_16,initImage时会创造一个空的icon
            //找到空图片的位置，记录空图片坐标
            if (tempArr[i] == 16) {
                x = i / 4;
                y = i % 4;
            }
            data[i / 4][i % 4] = tempArr[i];
        }

//        for(int i = 0; i < 4;i++){
//            for(int j = 0; j < 4; j++){
//                System.out.print(data[i][j] +" ");
//            }
//        }
    }

    private void initImage() {
        //每次开始前清空已有图片
        this.getContentPane().removeAll();
        if(judgeWin()){
            JLabel win = new JLabel(new ImageIcon("win/win.png"));
            win.setBounds(280,300,140,100);
            this.getContentPane().add(win);
        }

        JLabel stepCount = new JLabel("步数：" + step);
        stepCount.setBounds(10,8,100,30);
        this.getContentPane().add(stepCount);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int num = data[i][j];

                ImageIcon icon = new ImageIcon("soyoImage/piece_" + num + ".png");

                JLabel jl = new JLabel(icon);

                //指定图片位置
                jl.setBounds(172 * j + 25, 172 * i + 30, 172, 172);
                //添加边框
                jl.setBorder(new BevelBorder(BevelBorder.LOWERED));

                this.getContentPane().add(jl);
            }
        }


        //刷新
        this.getContentPane().repaint();

    }

    private void initJMenuBar() {
        //初始化菜单
        JMenuBar jmb = new JMenuBar();

        JMenu functionJM = new JMenu("功能");
        JMenu aboutJM = new JMenu("关于我们");

        functionJM.add(restartItem);
        functionJM.add(reLoginItem);
        functionJM.add(closeItem);

        aboutJM.add(accountItem);

        //绑定监听
        restartItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);

        jmb.add(functionJM);
        jmb.add(aboutJM);

        //将菜单放入界面
        this.setJMenuBar(jmb);
    }

    private void initJFrame() {
        //设置界面的初始的长宽
        this.setSize(750, 800);
        //设置标题
        this.setTitle("拼图 V1.0.0");
        //界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭方式
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //取消默认放置
        this.setLayout(null);

        //设置监听
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 16) {
            //shift
            //每次开始前清空已有图片
            this.getContentPane().removeAll();
            JLabel cjl = new JLabel(new ImageIcon("soyoImage/CompleteImage.jpg"));
            cjl.setBounds(25, 30, 689, 689);

            this.getContentPane().add(cjl);
            //刷新
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(judgeWin()){
            return;
        }
        int code = e.getKeyCode();
        if (code == 38) {
            //上
            if (x != 3) {
                step++;
                swapImage(x, y, x + 1, y);
                x = x + 1;
                initImage();
            }
        } else if (code == 40) {
            //下
            if (x != 0) {
                step++;
                swapImage(x, y, x - 1, y);
                x = x - 1;
                initImage();
            }
        } else if (code == 37) {
            //左
            if (y != 3) {
                step++;
                swapImage(x, y, x, y + 1);
                y = y + 1;
                initImage();
            }
        } else if (code == 39) {
            //右
            if (y != 0) {
                step++;
                swapImage(x, y, x, y - 1);
                y = y - 1;
                initImage();
            }
        } else if (code == 16) {
            //松开shift时复原
            initImage();
        } else if (code == 10) {
            //Enter:一键胜利
            int flag = 1;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    data[i][j] = flag;
                    flag++;
                }
            }
            initImage();
        }

    }

    private void swapImage(int x1, int y1, int x2, int y2) {
        int temp = data[x1][y1];
        data[x1][y1] = data[x2][y2];
        data[x2][y2] = temp;
    }

    private boolean judgeWin() {
        int flag = 1;
        //当结果正确时，data数组中的元素有序排列
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(data[i][j] != flag){
                    return false;
                }
                flag++;
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj  = e.getSource();
        if(obj == restartItem){
            initData();
            initImage();

            //显示界面
            this.setVisible(true);
        } else if (obj == reLoginItem) {
            this.setVisible(false);
            new LoginJFrame();
        } else if (obj == closeItem) {
            System.exit(0);
        } else if (obj == accountItem) {
            JDialog jDialog = new JDialog();
            JLabel jLabel = new JLabel(new ImageIcon("PersonalAccount/QRCode.png"));
            jLabel.setBounds(0,0,148,148);

            jDialog.getContentPane().add(jLabel);
            jDialog.setSize(344,344);
            jDialog.setAlwaysOnTop(true);
            jDialog.setLocationRelativeTo(null);
            //不关闭无法操作
            jDialog.setModal(true);
            jDialog.setVisible(true);
        }
    }
}
