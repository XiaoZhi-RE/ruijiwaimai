package com.xiaozhi.utils;

import java.util.Random;

/**
 * @author 20232
 */
public class VerifyCodeUtils {

    /**
     * 生成数字和字母组合验证码
     *
     * @param codeLength 验证码长度
     * @return char[] 字符
     */
    public static char[] getComboCode(int codeLength) {
        String chars = "0123456789abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] rands = new char[codeLength];

        for (int i = 0; i < codeLength; i++) {
            rands[i] = chars.charAt((int) (Math.random() * (26 * 2 + 10)));
        }
        return rands;
    }


    /**
     * 生成数字验证码
     *
     * @param codeLength 验证码长度
     * @return String字符串
     */
    public static String getNumberCode(int codeLength) {
        StringBuilder stringBuffer = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < codeLength; i++) {
            stringBuffer.append(random.nextInt(10));
        }
        return stringBuffer.toString();

    }
}
