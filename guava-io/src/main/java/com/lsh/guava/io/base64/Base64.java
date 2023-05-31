package com.lsh.guava.io.base64;

import com.google.common.base.Preconditions;

/**
 * @Author lishaohui
 * @Date 2023/5/27 18:44
 */
public final class Base64 {

    private static final String CODE_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz+/";

    private static final char[] CODE_DICT = CODE_STRING.toCharArray();

    private Base64() {
    }

    public static String encode(String plain) {
        Preconditions.checkNotNull(plain);
        StringBuilder result = new StringBuilder();
        String binaryStr = toBinary(plain);
        int delta = 6 - binaryStr.length() % 6;
        for (int i = 0; i < delta && delta != 6; i++) {
            binaryStr += "0";
        }
        for (int j = 0; j < binaryStr.length() / 6; j++) {
            int begin = j * 6;
            String encodeStr = binaryStr.substring(begin, begin + 6);
            char encodeChar = CODE_DICT[Integer.valueOf(encodeStr, 2)];
            result.append(encodeChar);
        }
        if (delta != 6) {
            for (int j = 0; j < delta / 2; j++) {
                result.append("=");
            }
        }
        return result.toString();
    }


    public static String decode(final String encrypt) {
        StringBuilder resultBuilder = new StringBuilder();
        String tmp = encrypt;
        int equalIndex = tmp.indexOf("=");
        if (equalIndex > 0) {
            tmp = tmp.substring(0, equalIndex);
        }
        String base64Binary = toBase64Binary(tmp);
        for (int j = 0; j < base64Binary.length() / 8; j++) {
            int begin = j * 8;
            String str = base64Binary.substring(begin, begin + 8);
            char c = Character.toChars(Integer.valueOf(str, 2))[0];
            resultBuilder.append(c);
        }
        return resultBuilder.toString();
    }


    private static String toBinary(final String source) {
        final StringBuilder binaryResult = new StringBuilder();
        for (int index = 0; index < source.length(); index++) {
            String charBin = Integer.toBinaryString(source.charAt(index));
            int delta = 8 - charBin.length();
            for (int d = 0; d < delta; d++)
                charBin = "0" + charBin;
            binaryResult.append(charBin);
        }
        return binaryResult.toString();
    }


    private static String toBase64Binary(final String source) {
        final StringBuilder binaryResult = new StringBuilder();
        for (int index = 0; index < source.length(); index++) {
            int i = CODE_STRING.indexOf(source.charAt(index));
            String charBinary = Integer.toBinaryString(i);
            int delta = 6 - charBinary.length();
            for (int d = 0; d < delta; d++) {
                charBinary = "0" + charBinary;
            }
            binaryResult.append(charBinary);
        }
        return binaryResult.toString();
    }


}
