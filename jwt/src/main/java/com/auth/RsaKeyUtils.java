package com.auth;

import java.io.*;
import java.security.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : pc
 * @date : 2020/8/25
 */
public class RsaKeyUtils {

    private static final String SALF = "zhaoxuan";

    public static KeyPair createKey() throws NoSuchAlgorithmException {
        // 首先生成一个KeyPairGenerator对象，用于生成非对称公私钥，实例化时指定类型为“RSA”
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        // 根据salt创建一个随机源
        SecureRandom secureRandom = new SecureRandom(SALF.getBytes());
        // 对KeyPairGenerator对象执行初始化，其中1024代表密钥大小，一般就取1024即可；参数二为随机源
        keyPairGenerator.initialize(1024, secureRandom);
        // 生成公私钥，“genKeyPair()”方法与“generateKeyPair()”方法相同，都能用
        return keyPairGenerator.genKeyPair();
        // 若需要将密钥写入文件，可以对生成的公私钥执行“对象名.getEncoded()”方法将密钥转换为“byte[]”，再写入文件
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        KeyPair key = createKey();
        byte[] privateEncoded = key.getPrivate().getEncoded();
        OutputStream privateOutputStream = new FileOutputStream(new File("C:\\Users\\pc\\Desktop\\private-key.pem"));
        privateOutputStream.write(privateEncoded);
        byte[] publicEncoded = key.getPublic().getEncoded();
        OutputStream publicOutputStream = new FileOutputStream(new File("C:\\Users\\pc\\Desktop\\public-key.pem"));
        publicOutputStream.write(publicEncoded);
    }
}
