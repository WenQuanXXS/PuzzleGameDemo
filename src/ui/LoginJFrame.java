package ui;

import object.User;
import util.CodeUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginJFrame extends JFrame implements ActionListener {
    JButton loginButton = new JButton();
    JButton registerButton = new JButton();

    JTextField jTextField = new JTextField();
    JPasswordField jPasswordField = new JPasswordField();
    JTextField codeField = new JTextField();

    String code = CodeUtil.createVerificationCode();

    ArrayList<User>  list = new ArrayList<User>();

    public LoginJFrame(){
        initJFrame();

        initImage();

        initData();
    }

    private void initData() {
        User u1 = new User("sasuke","amtls");
        User u2 = new User("WenQuanXXS","wudihuoyingdawang");
        list.add(u1);
        list.add(u2);
    }


    private void initImage() {
        //每次开始前清空已有图片
        this.getContentPane().removeAll();

        //验证码生成
//        System.out.println(code);
        JLabel correctCode = new JLabel(code);
        correctCode.setBounds(300,350,100,30);
        this.getContentPane().add(correctCode);

        //添加登录按钮
        loginButton.setBounds(100,400,126,66);
        loginButton.setIcon(new ImageIcon("button/loginButton.png"));
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        //绑定监听
        loginButton.addActionListener(this);
        this.getContentPane().add(loginButton);

        //添加注册按钮
        registerButton.setBounds(250,400,126,66);
        registerButton.setIcon(new ImageIcon("button/registerButton.png"));
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        //绑定监听
        registerButton.addActionListener(this);
        this.getContentPane().add(registerButton);

        //用户名输入框
        jTextField.setBounds(150,170,200,30);
        String s = jTextField.getText();
        this.getContentPane().add(jTextField);
        jTextField.setText("");

        //密码输入框
        jPasswordField.setBounds(150,270,200,30);
        this.getContentPane().add(jPasswordField);
        jPasswordField.setText("");

        //验证码输入框
        codeField.setBounds(150,350,100,30);
        this.getContentPane().add(codeField);
        codeField.setText("");

        //加载背景图片
        JLabel login = new JLabel(new ImageIcon("login/login.png"));
        login.setBounds(0,0,467,499);
        this.getContentPane().add(login);

        //刷新
        this.getContentPane().repaint();
    }

    private void initJFrame() {
        this.setSize(480,500);
        //设置标题
        this.setTitle("拼图 登录");
        //界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭方式
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //取消默认放置
        this.setLayout(null);
        //显示界面
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if(obj == loginButton){
            String username = jTextField.getText();
            String password = jPasswordField.getText();
            String tempCode = codeField.getText();

            if(getUserIndex(list,username) != -1){
                if(list.get(getUserIndex(list,username)).getPassword().equals(password)){
                    if(tempCode.equals(code)){
                        showDialog("登陆成功！");
                        //成功后重载并隐藏界面
                        initImage();
                        initJFrame();
                        this.setVisible(false);
                        new GameJFrame();
                    }else {
                        showDialog("用户名或密码或验证码错误");
                    }
                }else {
                    showDialog("用户名或密码或验证码错误");
                }
            }else {
                showDialog("用户名或密码或验证码错误");
            }
        } else if (obj == registerButton) {
            showDialog("功能开发中，敬请期待！");
        }
    }

    private void showDialog(String content){
        JDialog show = new JDialog();
        //设置弹窗大小
        show.setSize(200,150);
        //设置置顶
        show.setAlwaysOnTop(true);
        //设置居中
        show.setLocationRelativeTo(null);
        show.setModal(true);

        JLabel jLabel = new JLabel(content);
        jLabel.setBounds(0,0,200,150);
        show.getContentPane().add(jLabel);

        show.setVisible(true);
    }

    public static int getUserIndex(ArrayList<User> list, String id) {
        for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);
            String uid = u.getUid();
            if (uid.equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
