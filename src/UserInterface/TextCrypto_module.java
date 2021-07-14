package UserInterface;

import Entity.DESTool;
import Entity.User;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TextCrypto_module extends JFrame {
    private JLabel l2;
    private JLabel l3;
    private JTextArea textArea1;
    private JTextArea textArea2;

    /**
     * Create the frame.
     */
    public TextCrypto_module(JFrame outer) {
        outer.setExtendedState(JFrame.ICONIFIED);
        this.toFront();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("文字处理界面");
        setBounds(100, 100, 981, 619);
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

        JLabel l1 = new JLabel("输入内容可以进行加密和解密");
        l1.setFont(new Font("宋体", Font.PLAIN, 25));
        l1.setBounds(305, 15, 343, 90);
        contentPanel.add(l1);

        l2 = new JLabel("加密输入区");
        l2.setFont(new Font("宋体", Font.PLAIN, 22));
        l2.setBounds(136, 72, 116, 33);
        contentPanel.add(l2);

        l3 = new JLabel("解密输入区");
        l3.setFont(new Font("宋体", Font.PLAIN, 22));
        l3.setBounds(692, 72, 116, 33);
        contentPanel.add(l3);

        JButton Bnt1 = new JButton("加密");
        Bnt1.addActionListener(e -> {
            l3.setText("加密输出区");
            if (textArea1.getText().length() != 0) {
                if (!encrypt()) {
                    JOptionPane.showMessageDialog(null, "加密失败", "提示", JOptionPane.ERROR_MESSAGE);
                    textArea2.setText("");
                }
            }
        });
        Bnt1.setFont(new Font("宋体", Font.PLAIN, 24));
        Bnt1.setBounds(407, 165, 130, 49);
        contentPanel.add(Bnt1);

        JButton Bnt2 = new JButton("解密");
        Bnt2.addActionListener(e -> {
            l2.setText("解密输出区");
            if (textArea2.getText().length() != 0) {  //只有不是空才有必要解密
                if (!decrypt()) {
                    JOptionPane.showMessageDialog(null, "解密失败", "提示", JOptionPane.ERROR_MESSAGE);
                    textArea1.setText("");
                }
            }
        });
        Bnt2.setFont(new Font("宋体", Font.PLAIN, 24));
        Bnt2.setBounds(407, 313, 130, 49);
        contentPanel.add(Bnt2);

        textArea1 = new JTextArea();
        textArea1.setLineWrap(true);
        textArea1.setFont(new Font("Monospaced", Font.PLAIN, 25));

        textArea2 = new JTextArea();
        textArea2.setColumns(10);
        textArea2.setLineWrap(true);
        textArea2.setFont(new Font("Monospaced", Font.PLAIN, 25));

        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane1.setBounds(25, 110, 350, 420);
        scrollPane1.setViewportView(textArea1);
        contentPanel.add(scrollPane1);

        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane2.setBounds(575, 110, 350, 420);
        scrollPane2.setViewportView(textArea2);
        contentPanel.add(scrollPane2);

        JButton Bnt3 = new JButton("清空");
        Bnt3.addActionListener(arg0 -> reset());
        Bnt3.setFont(new Font("宋体", Font.PLAIN, 24));
        Bnt3.setBounds(407, 464, 130, 49);
        contentPanel.add(Bnt3);
    }

    private void reset() {
        l2.setText("加密输入区");
        l3.setText("解密输入区");
        this.textArea1.setText("");
        this.textArea2.setText("");
    }

    private boolean encrypt() {
        String buff = DESTool.getInstance().do_encrypt(this.textArea1.getText(), User.getInstance().getCrypto_key());
        this.textArea2.setText(buff);
        return !buff.equals("") || this.textArea1.getText().equals("");
    }

    private boolean decrypt() {
        String buff = DESTool.getInstance().do_decrypt(this.textArea2.getText(), User.getInstance().getCrypto_key());
        this.textArea1.setText(buff);
        return !buff.equals("") || this.textArea2.getText().equals("");
    }
}
