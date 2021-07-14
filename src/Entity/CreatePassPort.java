package Entity;

public class CreatePassPort {

    static {
        System.loadLibrary("Enc");
    }

    public static void main(String[] args) {
        //  注册码  MSRD8JS7BMA26    新的 I89K2CMF00NH4
        String a = BitwiseChange.byte_mac2char_mac(User.getInstance().getMac());
        String cipher = DESTool.getInstance().do_encrypt(a, DESTool.getInstance().getDefault_key());
        String ans = BitwiseChange.Parse_64bit_using32index(cipher);
        System.out.println("您的注册码是：");
        System.out.println(ans);
        System.out.println("一个机器只能使用唯一的注册码");
    }

    // test1  验证动态链接库是否成功使用的代码
    /*
        DESTool des_tool = DESTool.getInstance();
        String a = des_tool.do_encrypt("romantic", BitwiseChange.Char2Byte("12345678"));
        String b = des_tool.do_decrypt(a, BitwiseChange.Char2Byte("12345678"));
        System.out.println(a);
        System.out.println(b);
    */
    // test2   原注册码——没有使用加密，直接转化MAC地址 7HLAE1SNM4
    /*
        String ans = BitwiseChange.Parse_MAC_ADDR(User.getInstance().getMac_addr());
        System.out.println("您的注册码是：");
        System.out.println(ans);
        System.out.println("一个机器只能使用唯一的注册码");
     */
}
