package UserInterface;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FuncSelect_module extends JFrame {
    /**
     * Create the frame.
     */
    public FuncSelect_module() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("功能选择界面");
        setBounds(100, 100, 920, 525);
        getContentPane().setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JButton Bnt1 = new JButton("加密解密文件");
        Bnt1.addActionListener(e -> new DocCrypto_module(this).setVisible(true));
        Bnt1.setFont(new Font("宋体", Font.PLAIN, 25));
        Bnt1.setBounds(297, 74, 291, 43);
        contentPanel.add(Bnt1);

        JButton Bnt2 = new JButton("加密解密文本");
        Bnt2.addActionListener(e -> new TextCrypto_module(this).setVisible(true));
        Bnt2.setFont(new Font("宋体", Font.PLAIN, 25));
        Bnt2.setBounds(297, 207, 291, 43);
        contentPanel.add(Bnt2);

        JButton Bnt3 = new JButton("注销本用户");
        Bnt3.addActionListener(e -> new Delete_module(this).setVisible(true));
        Bnt3.setFont(new Font("宋体", Font.PLAIN, 25));
        Bnt3.setBounds(297, 341, 291, 43);
        contentPanel.add(Bnt3);
    }
}