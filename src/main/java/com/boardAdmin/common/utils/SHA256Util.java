package com.boardAdmin.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

@Slf4j
public class SHA256Util {
    public static final String ENCRTPTION_KEY = "SHA-256";

    public static String encryptSHA256(String str) {
        String SHA = null;

        MessageDigest sh;
        try {
            sh = MessageDigest.getInstance(ENCRTPTION_KEY);
            sh.update(str.getBytes());
            byte[] byteData = sh.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte byteDatum : byteData) {
                stringBuffer.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
            }
            SHA = stringBuffer.toString();
        } catch (Exception e) {
            log.error("Encrypt Error - NoSuchAlgorithmException", e);
            return null;
        }
        return SHA;
    }
}
