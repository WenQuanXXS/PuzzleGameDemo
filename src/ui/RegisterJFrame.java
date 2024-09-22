package ui;

import javax.swing.*;

public class RegisterJFrame extends JFrame {
    public RegisterJFrame(){
        initJFrame();
    }

    private void initJFrame() {
        this.setSize(480,500);
        //设置标题
        this.setTitle("拼图 注册");
        //界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭方式
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //显示界面
        this.setVisible(true);
    }
}
