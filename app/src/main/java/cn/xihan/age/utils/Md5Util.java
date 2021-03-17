package cn.xihan.age.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/2 23:15
 * @介绍 :
 */
public class Md5Util {

    public static String MD5(String str) {
        char[] hexDigits = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f' };
        try {
            byte[] strTemp = str.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] tmp = mdTemp.digest();
            char[] strs = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                strs[k++] = hexDigits[byte0 >>> 4 & 0xf];
                strs[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(strs).toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }


    public static String MD55(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte[] b = md.digest();
            int i;
            StringBuffer buf = new StringBuffer();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            //System.out.println(e);
        }

        return result;

    }


    public static String getFileMD5(File file) {
        if (!file.exists()) {
            return "";
        }
        try (FileInputStream in = new FileInputStream(file)) {
            FileChannel channel = in.getChannel();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(buffer);
            return bytes2Hex(md.digest());
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 一个byte转为2个hex字符
     *
     * @param src byte数组
     * @return 16进制大写字符串
     */
    public static String bytes2Hex(byte[] src) {
        char[] res = new char[src.length << 1];
        final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        for (int i = 0, j = 0; i < src.length; i++) {
            res[j++] = hexDigits[src[i] >>> 4 & 0x0f];
            res[j++] = hexDigits[src[i] & 0x0f];
        }
        return new String(res);
    }


    private final static String[] hexArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /***
     * 获取指定的字符串的MD5
     */
    public static String CalcMD5(String originString) {
        try {
            //创建具有MD5算法的信息摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
            byte[] bytes = md.digest(originString.getBytes());
            //将得到的字节数组变成字符串返回
            String s = byteArrayToHex(bytes);
            return s.toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字节数组转换成十六进制，并以字符串的形式返回
     * 128位是指二进制位。二进制太长，所以一般都改写成16进制，
     * 每一位16进制数可以代替4位二进制数，所以128位二进制数写成16进制就变成了128/4=32位。
     */
    private static String byteArrayToHex(byte[] b){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(byteToHex(b[i]));
        }
        return sb.toString();
    }
    /**
     * 将一个字节转换成十六进制，并以字符串的形式返回
     */
    public static String byteToHex(byte b) {
        int n = b;
        if (n < 0)
            n = n + 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexArray[d1]+hexArray[d2];
    }

}

