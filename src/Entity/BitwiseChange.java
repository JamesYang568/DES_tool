package Entity;

import java.util.Base64;

public class BitwiseChange {
    public static int[] change32 = {16, 8, 4, 2, 1};
    public static int[] change64 = {128, 64, 32, 16, 8, 4, 2, 1};
    public static char[] change_char = new char[36];

    static {
        for (int i = 0; i < 10; i++) {
            change_char[i] = (char) ('0' + i);
        }
        for (int i = 0; i < 26; i++) {
            change_char[10 + i] = (char) ('A' + i);
        }
    }

    public static String Parse_MAC_ADDR(String bit_string) {  //将二进制mac地址转化成36进制的，也就是5位一取,需要将mac地址后面补两个0
        if (bit_string.length() != 48)
            return "";
        String tmp = bit_string + "00";
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            String bitwise = tmp.substring(i * 5, (i + 1) * 5);
            int addup = 0;
            for (int j = 0; j < 5; j++) {
                addup += (bitwise.charAt(j) - '0') * change32[j];
            }
            output.append(change_char[addup]);
        }
        return output.toString();
    }

    public static String Parse_64bit_using32index(String bit_string) {  //将二进制加密后的mac地址转化成36进制的，也就是5位一取,需要将mac地址后面补1个0，返回13长度的串
        if (bit_string.length() != 64)
            return "";
        String tmp = bit_string + "0";
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            String bitwise = tmp.substring(i * 5, (i + 1) * 5);
            int addup = 0;
            for (int j = 0; j < 5; j++) {
                addup += (bitwise.charAt(j) - '0') * change32[j];
            }
            output.append(change_char[addup]);
        }
        return output.toString();
    }

    public static String UnPack_MAC_ADDR(String bit32_string) {  //将13位的MAC地址根据32进制转化成二进制01串
        bit32_string = bit32_string.toUpperCase();
        if (bit32_string.length() != 10)
            return "";
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            StringBuilder tmp = new StringBuilder();
            int addup;
            if (bit32_string.charAt(i) - '0' < 10) {
                addup = bit32_string.charAt(i) - '0';
            } else {
                addup = bit32_string.charAt(i) - 'A' + 10;
            }
            while (addup != 0) {
                tmp.append(addup % 2);
                addup /= 2;
            }
            if (tmp.length() < 5)
                tmp.append("00000", 0, 5 - tmp.length());
            output.append(tmp.reverse());
        }
        return output.substring(0, 48);
    }

    public static String UnPack_32index_to64bit(String bit32_string) {  //将13位的MAC地址根据32进制转化成64二进制01串，删除最后一位
        bit32_string = bit32_string.toUpperCase();
        if (bit32_string.length() != 13)
            return "";
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            StringBuilder tmp = new StringBuilder();
            int addup;
            if (bit32_string.charAt(i) - '0' < 10) {
                addup = bit32_string.charAt(i) - '0';
            } else {
                addup = bit32_string.charAt(i) - 'A' + 10;
            }
            while (addup != 0) {
                tmp.append(addup % 2);
                addup /= 2;
            }
            if (tmp.length() < 5)
                tmp.append("00000", 0, 5 - tmp.length());
            output.append(tmp.reverse());
        }
        return output.substring(0, 64);
    }

    @Deprecated
    /*开始使用这个方法将二进制01串转化为char，发现会出现错误*/
    public static String Byte2Char(String bit_string) {
        StringBuilder output = new StringBuilder();
        if (bit_string.length() != 64) {
            StringBuilder bit_stringBuilder = new StringBuilder(bit_string);
            for (int i = bit_stringBuilder.length(); i < 64; i++) {
                bit_stringBuilder.append("0");
            }
            bit_string = bit_stringBuilder.toString();
        }
        for (int i = 0; i < 8; i++) {
            int addup = 0;
            String tmp = bit_string.substring(i * 8, (i + 1) * 8);
            for (int j = 0; j < 8; j++) {
                addup += (tmp.charAt(j) - '0') * change64[j];
            }
            output.append((char) addup);
        }
        return output.toString();
    }

    /**
     * 使用这个方法可将二进制字符串的mac转化为string类型，本质上是调用了Base64
     */
    public static String byte_mac2char_mac(byte[] mac) {
        return Base64.getEncoder().encodeToString(mac);
    }

    /**
     * 传入char的字串，转成01串
     */
    public static String Char2Byte(String char_string) {
        if (char_string.length() != 8)
            return "";
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            StringBuilder tmp = new StringBuilder();
            int addup = char_string.charAt(i);
            while (addup != 0) {
                tmp.append(addup % 2);
                addup /= 2;
            }
            if (tmp.length() < 8)
                tmp.append("00000000", 0, 8 - tmp.length());
            output.append(tmp.reverse());
        }
        return output.toString();
    }
}
