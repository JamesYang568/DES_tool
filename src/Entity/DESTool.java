package Entity;

public class DESTool {
    private final String default_key;  //默认秘钥

    private static DESTool des_tool = null;

    public static DESTool getInstance() {
        if (des_tool == null)
            des_tool = new DESTool();
        return des_tool;
    }

    /**
     * 传入明文和01密码串，返回01加密串
     */
    public native String do_encrypt(String Paint_text, String theKey);

    /**
     * 传入密文和01密码串，返回明文串
     */
    public native String do_decrypt(String cipher_text, String theKey);

    private DESTool() {
        this.default_key = BitwiseChange.Char2Byte("12345678");
    }

    public String getDefault_key() {
        return default_key;
    }
}
