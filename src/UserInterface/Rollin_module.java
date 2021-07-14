package UserInterface;

import Entity.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Rollin_module extends JDialog {
    private final JTextField textF;
    private final JPasswordField passwordF1;
    private final JPasswordField passwordF2;

    /**
     * Create the dialog.
     */
    public Rollin_module() {
        setTitle("新用户注册");
        setBounds(100, 100, 906, 671);
        getContentPane().setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel l1 = new JLabel("请输入用户名和注册密码");
        l1.setFont(new Font("宋体", Font.PLAIN, 25));
        l1.setBounds(290, 34, 284, 50);
        contentPanel.add(l1);

        JLabel l2 = new JLabel("请输入用户名");
        l2.setFont(new Font("宋体", Font.PLAIN, 25));
        l2.setBounds(119, 144, 178, 50);
        contentPanel.add(l2);

        textF = new JTextField();
        textF.setFont(new Font("宋体", Font.PLAIN, 24));
        textF.setBounds(419, 145, 304, 50);
        contentPanel.add(textF);
        textF.setColumns(15);

        JLabel l3 = new JLabel("请输入密码");
        l3.setFont(new Font("宋体", Font.PLAIN, 25));
        l3.setBounds(119, 265, 156, 50);
        contentPanel.add(l3);

        JLabel l4 = new JLabel("请再次输入密码");
        l4.setFont(new Font("宋体", Font.PLAIN, 25));
        l4.setBounds(119, 388, 187, 50);
        contentPanel.add(l4);

        JLabel l5 = new JLabel("两次密码不一致");
        l5.setForeground(Color.RED);
        l5.setFont(new Font("宋体", Font.PLAIN, 24));
        l5.setBounds(119, 330, 187, 36);
        l5.setVisible(false);
        contentPanel.add(l5);

        passwordF1 = new JPasswordField();
        passwordF1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent arg0) {
                l5.setVisible(false);
            }
        });
        passwordF1.setFont(new Font("宋体", Font.PLAIN, 21));
        passwordF1.setBounds(419, 268, 304, 48);
        contentPanel.add(passwordF1);

        passwordF2 = new JPasswordField();
        passwordF2.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                l5.setVisible(false);
            }
        });
        passwordF2.setFont(new Font("宋体", Font.PLAIN, 21));
        passwordF2.setBounds(419, 388, 304, 48);
        contentPanel.add(passwordF2);

        JButton Bnt1 = new JButton("注册");
        Bnt1.addActionListener(e -> {
            if (new String(passwordF1.getPassword()).equals(new String(passwordF2.getPassword()))) {
                if (add_user()) {
                    JOptionPane.showMessageDialog(null, "注册成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "用户名已经注册", "提示", JOptionPane.ERROR_MESSAGE);
                    reset();
                }
            } else {
                l5.setVisible(true);
            }
        });
        Bnt1.setFont(new Font("宋体", Font.PLAIN, 24));
        Bnt1.setBounds(210, 518, 148, 50);
        contentPanel.add(Bnt1);

        JButton Bnt2 = new JButton("清空");
        Bnt2.addActionListener(e -> reset());
        Bnt2.setFont(new Font("宋体", Font.PLAIN, 24));
        Bnt2.setBounds(519, 518, 148, 50);
        contentPanel.add(Bnt2);
    }

    private void reset() {
        this.passwordF1.setText("");
        this.passwordF2.setText("");
        this.textF.setText("");
    }

    private boolean add_user() {
        return User.getInstance().addUser(this.textF.getText(), new String(this.passwordF1.getPassword()));
    }
}
