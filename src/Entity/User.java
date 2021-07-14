package Entity;

import java.io.*;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;

public class User {
    private String user_name;
    private byte[] mac;   //Mac地址的byte类型
    private String mac_addr;
    private String disk_addr;
    private String crypto_key;  //保存秘钥的明文(01串)

    private InetAddress inetAddress;
    private final PrefsHandler prefsHandler = PrefsHandler.getInstance();
    private final DESTool des_tool = DESTool.getInstance();

    private static User user = null;

    private User() {
        try {
            inetAddress = InetAddress.getLocalHost();
            this.getMAC_addr();
            this.getDISK_addr();
            this.crypto_key = String.valueOf((this.mac_addr + this.disk_addr.substring(0, 16)).toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User getInstance() {
        if (user == null)
            user = new User();
        return user;
    }

    /**
     * 获取本机的MAC地址
     */
    private void getMAC_addr() throws SocketException {
        mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
        this.mac_addr = new BigInteger(1, mac).toString(2);
        if (this.mac_addr.length() < 48) {
            char[] chars = new char[48 - this.mac_addr.length()];
            Arrays.fill(chars, '0');
            String s = new String(chars);
            this.mac_addr = s + this.mac_addr;
        }
    }

    /**
     * 获取本机的磁盘序列号
     * 通过写一个VB脚本运行得到
     */
    private void getDISK_addr() {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);  //写一个VB文件运行
            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    + "Set colDrives = objFSO.Drives\n"
                    + "Set objDrive = colDrives.item(\"" + "C" + "\")\n"
                    + "Wscript.Echo objDrive.SerialNumber";  // see note
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.disk_addr = result.trim();
        //转成2进制字符串
        char[] strChar = this.disk_addr.toCharArray();
        String tmp = "";
        for (char c : strChar) {
            tmp += Integer.toBinaryString(c) + " ";
        }
        this.disk_addr = tmp;
    }

    /**
     * 判断软件是否已经注册
     * 已经注册返回true
     */
    public boolean hasRegister() {
        //对mac地址进行加密
        String a = BitwiseChange.byte_mac2char_mac(mac);  //将mac转化为char字符串
        String tmp = DESTool.getInstance().do_encrypt(a, des_tool.getDefault_key());
        //检测注册表里面是否已经将软件注册，如果比对上那么就一样，否则弹出注册界面
        //String value = prefsHandler.findRegisterList("yang_software");
        String value = prefsHandler.find_sys_RegisterList("yang_software");
        return value.equals(tmp);
    }

    /**
     * 进行注册，传入注册码
     * 注意这里只是对MAC地址进行了加密  注册成功返回true
     */
    public boolean Register(String text) {  //进行注册，加密核对正确，在注册表中写入信息
        String a = BitwiseChange.byte_mac2char_mac(User.getInstance().getMac());  //将mac转化为char字符串
        String value = DESTool.getInstance().do_encrypt(a, des_tool.getDefault_key());
        String unpack = BitwiseChange.UnPack_32index_to64bit(text);  //要注册码先解码
        if (value.equals(unpack)) {
            prefsHandler.set_sys_RegisterList("yang_software", value);
            return true;
        } else
            return false;
    }

    /**
     * 注册一个用户的时候需要调用这个方法，返回是否注册成功
     */
    public boolean addUser(String user_name, String password) {
        if (!prefsHandler.find_sys_RegisterList(user_name).equals(""))   //如果用户名已经使用，则不能注册
            return false;
        else { //写入注册表
            this.user_name = user_name;
            String psw = des_tool.do_encrypt(password, this.crypto_key); //获得加密的密码
            if (psw.equals(""))
                return false;
            prefsHandler.set_sys_RegisterList(user_name, psw);
            return true;
        }
    }

    /**
     * 用户登录检查使用这个方法，返回是否存在该用户
     */
    public boolean identifying(String user_name, String input_password) {
        //将得到的password进行加密，之后读取注册表找到对应用户名的密码对比
        String tmp_psw = des_tool.do_encrypt(input_password, this.crypto_key);
        return prefsHandler.find_sys_RegisterList(user_name).equals(tmp_psw);
    }

    /**
     * 删除用户时调用该方法，返回是否正确删除
     */
    public boolean deleteUser(String input_password) {
        String tmp_psw = des_tool.do_encrypt(input_password, this.crypto_key);
        return prefsHandler.delete_sys_RegisterList(this.user_name, tmp_psw);
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCrypto_key() {
        return crypto_key;
    }

    // 只是为了在CreatePassPort中使用mac变量才放开了mac地址的调用
    public byte[] getMac() {
        return mac;
    }
}
