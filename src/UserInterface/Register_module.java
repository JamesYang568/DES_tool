package UserInterface;

import Entity.User;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Register_module extends JFrame {
    private final JTextField textF;
    private final User user = User.getInstance();

    /**
     * Create the frame.
     */
    public Register_module() {
        setTitle("软件未注册");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 847, 577);
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);

        contentPanel.setLayout(null);

        JLabel l1 = new JLabel("您还没有注册本软件，请输入软件注册码进行注册");
        l1.setFont(new Font("宋体", Font.PLAIN, 25));
        l1.setBounds(150, 15, 565, 67);
        contentPanel.add(l1);

        JLabel l2 = new JLabel("请在这里填写您的注册码：");
        l2.setFont(new Font("宋体", Font.PLAIN, 24));
        l2.setBounds(33, 128, 325, 46);
        contentPanel.add(l2);

        JLabel lwrong = new JLabel("您的注册码错误");
        lwrong.setForeground(Color.RED);
        lwrong.setFont(new Font("宋体", Font.PLAIN, 24));
        lwrong.setBounds(459, 189, 185, 46);
        lwrong.setVisible(false);
        contentPanel.add(lwrong);

        textF = new JTextField();
        textF.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                lwrong.setVisible(false);
            }
        });
        textF.setFont(new Font("宋体", Font.PLAIN, 24));
        textF.setBounds(344, 132, 402, 42);
        contentPanel.add(textF);
        textF.setColumns(10);

        JLabel lsucceed = new JLabel("注册成功，感谢您的支持，系统将跳转到登录界面");
        lsucceed.setFont(new Font("宋体", Font.PLAIN, 24));
        lsucceed.setBounds(142, 280, 547, 46);
        contentPanel.add(lsucceed);
        lsucceed.setVisible(false);

        JPanel buttonPane = new JPanel();
        buttonPane.setBounds(0, 356, 834, 173);
        getContentPane().add(buttonPane);
        buttonPane.setLayout(null);

        JButton Bntens = new JButton("注册");
        Bntens.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (register()) {
                    lsucceed.setVisible(true);
                    JOptionPane.showMessageDialog(null, "软件注册成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    new Login_module();
                    dispose();
                } else {
                    lwrong.setVisible(true);
                    reset();
                }
            }
        });
        Bntens.setFont(new Font("宋体", Font.PLAIN, 25));
        Bntens.setBounds(179, 64, 164, 55);
        Bntens.setActionCommand("OK");
        buttonPane.add(Bntens);

        JButton Bntrst = new JButton("清空");
        Bntrst.addActionListener(e -> reset());
        Bntrst.setFont(new Font("宋体", Font.PLAIN, 25));
        Bntrst.setBounds(478, 64, 173, 55);
        Bntrst.setActionCommand("Cancel");
        buttonPane.add(Bntrst);
    }

    private void reset() {
        this.textF.setText("");
    }

    private boolean register() {
        return User.getInstance().Register(this.textF.getText());
    }
}
