package UserInterface;

import Entity.DESTool;
import Entity.MS_Word_Wrapper;
import Entity.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

public class DocCrypto_module extends JFrame {

    private final JPanel contentPanel = new JPanel();
    private static final String current_path = "./";
    private final JTextField textF1;
    private final JTextField textF2;
    private final JTextField textF3;
    private final JTextField textF4;

    /**
     * Create the frame.
     */
    public DocCrypto_module(JFrame outer) {
        outer.setExtendedState(JFrame.ICONIFIED);
        this.toFront();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setTitle("文档处理界面");
        setBounds(100, 100, 1019, 659);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                outer.setExtendedState(JFrame.NORMAL);
                outer.toFront();
            }
        });

        JLabel l1 = new JLabel("选择加密文件");
        l1.setFont(new Font("宋体", Font.PLAIN, 25));
        l1.setBounds(29, 63, 191, 75);
        contentPanel.add(l1);

        JLabel l2 = new JLabel("选择处理后文件保存地址");
        l2.setFont(new Font("宋体", Font.PLAIN, 25));
        l2.setBounds(29, 151, 300, 75);
        contentPanel.add(l2);

        textF1 = new JTextField();
        textF1.setEditable(false);
        textF1.setFont(new Font("宋体", Font.PLAIN, 23));
        textF1.setBounds(369, 74, 387, 53);
        contentPanel.add(textF1);
        textF1.setColumns(10);

        textF2 = new JTextField("默认保存在_encrypt文件中,可不选择");
        textF2.setEditable(false);
        textF2.setFont(new Font("宋体", Font.PLAIN, 23));
        textF2.setColumns(10);
        textF2.setBounds(369, 162, 387, 53);
        contentPanel.add(textF2);

        JLabel l3 = new JLabel("选择解密文件");
        l3.setFont(new Font("宋体", Font.PLAIN, 25));
        l3.setBounds(41, 333, 191, 75);
        contentPanel.add(l3);

        JLabel l4 = new JLabel("选择处理后文件保存地址,可不选择");
        l4.setFont(new Font("宋体", Font.PLAIN, 25));
        l4.setBounds(41, 423, 275, 75);
        contentPanel.add(l4);

        textF3 = new JTextField();
        textF3.setEditable(false);
        textF3.setFont(new Font("宋体", Font.PLAIN, 23));
        textF3.setColumns(10);
        textF3.setBounds(369, 344, 369, 53);
        contentPanel.add(textF3);

        textF4 = new JTextField("默认保存在_decrypt文件中,可不选择");
        textF4.setEditable(false);
        textF4.setFont(new Font("宋体", Font.PLAIN, 23));
        textF4.setColumns(10);
        textF4.setBounds(369, 434, 369, 53);
        contentPanel.add(textF4);

        JButton Bnts1 = new JButton("选择");
        Bnts1.addActionListener(e -> choose_path(1));
        Bnts1.setFont(new Font("宋体", Font.PLAIN, 24));
        Bnts1.setBounds(813, 79, 134, 43);
        contentPanel.add(Bnts1);

        JButton Bnts2 = new JButton("选择");
        Bnts2.addActionListener(e -> choose_path(2));
        Bnts2.setFont(new Font("宋体", Font.PLAIN, 24));
        Bnts2.setBounds(813, 167, 134, 43);
        contentPanel.add(Bnts2);

        JButton Bnten = new JButton("加密");
        Bnten.addActionListener(e -> {
            try {
                if (!encrypt()) {
                    JOptionPane.showMessageDialog(null, "加密错误", "提示", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "加密成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
                this.textF1.setText("");
                this.textF2.setText("默认保存在_encrypt文件中,可不选择");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        Bnten.setFont(new Font("宋体", Font.PLAIN, 25));
        Bnten.setBounds(396, 241, 142, 53);
        contentPanel.add(Bnten);

        JButton Bnts3 = new JButton("选择");
        Bnts3.addActionListener(e -> choose_path(3));
        Bnts3.setFont(new Font("宋体", Font.PLAIN, 24));
        Bnts3.setBounds(813, 349, 134, 43);
        contentPanel.add(Bnts3);

        JButton Bnts4 = new JButton("选择");
        Bnts4.addActionListener(e -> choose_path(4));
        Bnts4.setFont(new Font("宋体", Font.PLAIN, 24));
        Bnts4.setBounds(813, 439, 134, 43);
        contentPanel.add(Bnts4);

        JButton Bntde = new JButton("解密");
        Bntde.addActionListener(e -> {
            try {
                if (!decrypt()) {
                    JOptionPane.showMessageDialog(null, "解密错误", "提示", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "解密成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
                textF3.setText("");
                textF4.setText("默认保存在_decrypt文件中,可不选择");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        Bntde.setFont(new Font("宋体", Font.PLAIN, 25));
        Bntde.setBounds(396, 513, 142, 53);
        contentPanel.add(Bntde);
    }

    private void choose_path(int i) {
        JFileChooser fileChooser = new JFileChooser(current_path);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int option = fileChooser.showOpenDialog(contentPanel);
        String path;
        if (option == JFileChooser.APPROVE_OPTION) {  //判断选择什么操作
            path = fileChooser.getSelectedFile().getPath();
        } else {
            path = "请选择文件";
        }
        switch (i) {
            case 1:
                this.textF1.setText(path);
                break;
            case 2:
                this.textF2.setText(path);
                break;
            case 3:
                this.textF3.setText(path);
                break;
            case 4:
                this.textF4.setText(path);
                break;
        }
    }

    /**
     * 直接往文件里面写01串
     */
    private boolean encrypt() throws IOException {
        String get_name = this.textF1.getText();
        String save_name = this.textF2.getText();
        if (invalid(get_name))  //判断输入地址是否有效
            return false;
        //判断输出路径是否正确
        if (save_name.equals("默认保存在_encrypt文件中,可不选择") || save_name.equals("请选择文件") || save_name.equals(""))  //默认加密后文件地址
            save_name = get_name.split("\\.")[0] + "_encrypt." + get_name.split("\\.")[1];
        else if (invalid(save_name))  //看一下是否格式正确
            return false;
        String[] plain;
        ArrayList<String> cipher = new ArrayList<>();
        if (get_name.endsWith(".txt")) {
            plain = MS_Word_Wrapper.read_txt(get_name);
            for (String s : plain) {
                cipher.add(DESTool.getInstance().do_encrypt(s, User.getInstance().getCrypto_key()));
            }
            MS_Word_Wrapper.write_txt_file(save_name, cipher.toArray(new String[0]));
        } else if (get_name.endsWith(".doc")) {
            plain = MS_Word_Wrapper.read_doc(get_name);
            for (String s : plain) {
                cipher.add(DESTool.getInstance().do_encrypt(s, User.getInstance().getCrypto_key()));
            }
            MS_Word_Wrapper.write_docx_file(save_name + 'x', cipher.toArray(new String[0]));
        } else {
            plain = MS_Word_Wrapper.read_docx(get_name);
            for (String s : plain) {
                cipher.add(DESTool.getInstance().do_encrypt(s, User.getInstance().getCrypto_key()));
            }
            MS_Word_Wrapper.write_docx_file(save_name, cipher.toArray(new String[0]));
        }
        return !cipher.get(0).equals("") || plain[0].length() != 0;
    }

    /**
     * 要解密的文件应该是01串的格式
     */
    private boolean decrypt() throws IOException {
        String get_name = this.textF3.getText();
        String save_name = this.textF4.getText();
        if (invalid(get_name))  //判断输入地址是否有效
            return false;
        //判断输出路径是否正确
        if (save_name.equals("默认保存在_decrypt文件中,可不选择") || save_name.equals("请选择文件") || save_name.equals(""))  //默认加密后文件地址
            save_name = get_name.split("\\.")[0] + "_decrypt." + get_name.split("\\.")[1];
        else if (invalid(save_name))  //看一下是否格式正确
            return false;
        ArrayList<String> plain = new ArrayList<>();
        String[] cipher;  //防止存在多行的情况
        if (get_name.endsWith(".txt")) {
            //读文件
            cipher = MS_Word_Wrapper.read_txt(get_name);
            //解密
            for (String s : cipher) {
                plain.add(DESTool.getInstance().do_decrypt(s, User.getInstance().getCrypto_key()));
            }
            //写文件
            MS_Word_Wrapper.write_txt_file(save_name, plain.toArray(new String[0]));
        } else if (get_name.endsWith(".doc")) {
            //如果错误的选择了doc作为解密文件，则需要后面加x打开
            cipher = MS_Word_Wrapper.read_docx(get_name);
            for (String s : cipher) {
                plain.add(DESTool.getInstance().do_decrypt(s, User.getInstance().getCrypto_key()));
            }
            MS_Word_Wrapper.write_docx_file(save_name + 'x', plain.toArray(new String[0]));
        } else {
            cipher = MS_Word_Wrapper.read_docx(get_name);
            for (String s : cipher) {
                plain.add(DESTool.getInstance().do_decrypt(s, User.getInstance().getCrypto_key()));
            }
            MS_Word_Wrapper.write_docx_file(save_name, plain.toArray(new String[0]));
        }
        return !cipher[0].equals("") || plain.get(0).length() != 0;
    }

    /**
     * 判断文件的路径是否格式正确,不正确则返回true
     */
    private boolean invalid(String get_name) {
        String[] sp = get_name.split("\\.");
        if (get_name.equals("请选择文件")) {
            JOptionPane.showMessageDialog(null, "有文件地址未选择！", "提示", JOptionPane.ERROR_MESSAGE);
            return true;
        } else if (get_name.equals("")) {
            JOptionPane.showMessageDialog(null, "请选择文件地址！", "提示", JOptionPane.ERROR_MESSAGE);
            return true;
        } else if (sp.length == 1) {
            JOptionPane.showMessageDialog(null, "路径错误！", "提示", JOptionPane.ERROR_MESSAGE);
            return true;
        } else if (!sp[sp.length - 1].equals("txt") && !sp[sp.length - 1].equals("doc") && !sp[sp.length - 1].equals("docx")) {
            JOptionPane.showMessageDialog(null, "文件无法读取！", "提示", JOptionPane.ERROR_MESSAGE);
            return true;
        } else
            return false;
    }
}
