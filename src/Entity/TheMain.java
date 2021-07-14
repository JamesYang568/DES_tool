package Entity;

import UserInterface.Login_module;
import UserInterface.Register_module;

import java.awt.*;

public class TheMain {
    //类的名字不能有下划线否则没有办法识别dll中方法所对应的类
    static {
        System.loadLibrary("Enc");
    }

    //  注册码  I89K2CMF00NH4
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    if (!User.getInstance().hasRegister()) {
                        Register_module register_module = new Register_module();
                        register_module.setVisible(true);
                    } else {
                        new Login_module();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
