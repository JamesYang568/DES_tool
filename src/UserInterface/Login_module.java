package UserInterface;

import Entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login_module {
    private final JFrame frame;
    private JTextField textF;
    private JPasswordField pswF;
    private final User user = User.getInstance();

    /**
     * Create the application.
     */
    public Login_module() {
        frame = new JFrame();
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame.setTitle("登录加密系统");
        frame.setFont(new Font("Dialog", Font.PLAIN, 20));
        frame.getContentPane().setFont(new Font("宋体", Font.PLAIN, 20));
        frame.getContentPane().setLayout(null);
        JPanel panel = new JPanel();
        panel.setBounds(0, 157, 959, 430);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        panel.setLayout(null);

        JLabel l1 = new JLabel("欢迎使用加密系统");
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        l1.setFont(new Font("宋体", Font.PLAIN, 32));
        l1.setBounds(331, 41, 287, 60);
        frame.getContentPane().add(l1);

        JLabel l2 = new JLabel("powered by 杨嘉雄");
        l2.setFont(new Font("楷体", Font.PLAIN, 20));
        l2.setBounds(774, 121, 170, 37);
        frame.getContentPane().add(l2);
        frame.setBounds(100, 100, 981, 643);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel l3 = new JLabel("您已经注册该软件   请登录本系统");
        l3.setForeground(Color.RED);
        l3.setFont(new Font("宋体", Font.PLAIN, 28));
        l3.setBounds(238, 15, 455, 66);
        panel.add(l3);

        JLabel l4 = new JLabel("用户名");
        l4.setFont(new Font("宋体", Font.PLAIN, 25));
        l4.setBounds(222, 141, 92, 38);
        panel.add(l4);

        JLabel l5 = new JLabel("密码");
        l5.setFont(new Font("宋体", Font.PLAIN, 25));
        l5.setBounds(222, 253, 77, 38);
        panel.add(l5);

        textF = new JTextField();
        textF.setFont(new Font("宋体", Font.PLAIN, 22));
        textF.setBounds(468, 141, 280, 38);
        panel.add(textF);
        textF.setColumns(15);

        pswF = new JPasswordField();
        pswF.setColumns(10);
        pswF.setFont(new Font("宋体", Font.PLAIN, 18));
        pswF.setBounds(468, 254, 280, 38);
        panel.add(pswF);

        JButton Bnt1 = new JButton("登录");
        Bnt1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (login()) {
                    user.setUser_name(textF.getText());
                    JOptionPane.showMessageDialog(null, "登录成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    new FuncSelect_module().setVisible(true);
                    frame.dispose();
                } else {
                    textF.setText("");
                    pswF.setText("");
                    JOptionPane.showMessageDialog(null, "用户名或密码错误", "提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        Bnt1.setFont(new Font("宋体", Font.PLAIN, 25));
        Bnt1.setBounds(238, 347, 136, 38);
        panel.add(Bnt1);

        JButton Bnt2 = new JButton("注册账号");
        Bnt2.addActionListener(e -> new Rollin_module().setVisible(true));
        Bnt2.setFont(new Font("宋体", Font.PLAIN, 25));
        Bnt2.setBounds(546, 347, 136, 38);
        panel.add(Bnt2);
    }

    private boolean login() {
        if (this.textF.getText().equals("") || new String(this.pswF.getPassword()).length() == 0)
            return false;
        return user.identifying(this.textF.getText(), new String(this.pswF.getPassword()));
    }
}
