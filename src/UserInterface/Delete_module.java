package UserInterface;

import Entity.User;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Delete_module extends JFrame {

    private final JTextField textF1;
    private final JTextField textF2;

    /**
     * Create the frame.
     */
    public Delete_module(JFrame outer) {
        outer.setExtendedState(JFrame.ICONIFIED);
        this.toFront();
        setTitle("注销用户");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 828, 631);
        getContentPane().setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                outer.setExtendedState(JFrame.NORMAL);
                outer.toFront();
            }
        });

        JLabel l1 = new JLabel("您现在正在删除您的账户");
        l1.setForeground(Color.RED);
        l1.setFont(new Font("宋体", Font.BOLD, 32));
        l1.setBounds(222, 26, 409, 91);
        contentPanel.add(l1);

        JLabel name_label = new JLabel(User.getInstance().getUser_name());
        name_label.setHorizontalAlignment(SwingConstants.CENTER);
        name_label.setFont(new Font("宋体", Font.PLAIN, 25));
        name_label.setBounds(113, 105, 598, 48);
        contentPanel.add(name_label);

        JLabel l2 = new JLabel("请输入您的密码");
        l2.setFont(new Font("宋体", Font.PLAIN, 25));
        l2.setBounds(97, 202, 177, 48);
        contentPanel.add(l2);

        JLabel l3 = new JLabel("请输入确认密码");
        l3.setFont(new Font("宋体", Font.PLAIN, 25));
        l3.setBounds(97, 337, 188, 48);
        contentPanel.add(l3);

        textF1 = new JTextField();
        textF1.setFont(new Font("宋体", Font.PLAIN, 24));
        textF1.setBounds(398, 199, 296, 56);
        contentPanel.add(textF1);
        textF1.setColumns(15);

        textF2 = new JTextField();
        textF2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent arg0) {
                name_label.setText("确定要注销您的账号");
                name_label.setForeground(Color.RED);
            }
        });
        textF2.setFont(new Font("宋体", Font.PLAIN, 24));
        textF2.setColumns(15);
        textF2.setBounds(398, 334, 296, 56);
        contentPanel.add(textF2);

        JButton button = new JButton("确认删除");
        button.addActionListener(arg0 -> {
            if (textF1.getText().equals(textF2.getText())) {
                if (!User.getInstance().deleteUser(textF1.getText())) {
                    JOptionPane.showMessageDialog(null, "密码错误", "提示", JOptionPane.ERROR_MESSAGE);
                    textF1.setText("");
                    textF2.setText("");
                } else {
                    name_label.setText("您已注销账号，系统退出");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);  //结束系统
                }
            } else {
                textF1.setText("");
                textF2.setText("");
                JOptionPane.showMessageDialog(null, "密码不一致", "提示", JOptionPane.ERROR_MESSAGE);
            }
        });
        button.setFont(new Font("宋体", Font.PLAIN, 24));
        button.setBounds(313, 492, 177, 48);
        contentPanel.add(button);
    }
}
