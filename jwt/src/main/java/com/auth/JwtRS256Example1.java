package com.auth;

import com.google.common.collect.Lists;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import lombok.extern.slf4j.Slf4j;

import java.security.*;
import java.util.Date;
import java.util.List;

/**
 * @author : pc
 * @date : 2020/8/25
 */
@Slf4j
public class JwtRS256Example1 {

    public static String salf = "xuan";
    public static long outTime = 1000 * 60 * 60L;

    public static KeyPair createKey() throws NoSuchAlgorithmException {
        // 首先生成一个KeyPairGenerator对象，用于生成非对称公私钥，实例化时指定类型为“RSA”
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        // 根据salt创建一个随机源
        SecureRandom secureRandom = new SecureRandom(salf.getBytes());
        // 对KeyPairGenerator对象执行初始化，其中1024代表密钥大小，一般就取1024即可；参数二为随机源
        keyPairGenerator.initialize(1024, secureRandom);
        // 生成公私钥，“genKeyPair()”方法与“generateKeyPair()”方法相同，都能用
        return keyPairGenerator.genKeyPair();
        // 若需要将密钥写入文件，可以对生成的公私钥执行“对象名.getEncoded()”方法将密钥转换为“byte[]”，再写入文件
    }

    public static String createJwt(Object userInfo, List<String> roles, List<String> permissions,PrivateKey privateKey){
        long millis = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("alg", "RS256")
                .setHeaderParam("typ", "JWT")
                .claim("userInfo", userInfo)
                .claim("roles", roles)
                .claim("permissions", permissions)
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .setExpiration(new Date(millis + outTime));
        return builder.compact();
    }

    public static Claims parserUserToken(String userToken, PublicKey publicKey) {
        // 通过公钥解析Token
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(userToken);
        // 获取携带内容体
        return claimsJws.getBody();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyPair key = createKey();
        String jwt = createJwt("zhaoxuan", Lists.newArrayList("admin"), Lists.newArrayList("all"),key.getPrivate());
        log.info("jwt:{}",jwt);
        Claims claims = parserUserToken(jwt, key.getPublic());
        Object userInfo = claims.get("userInfo");
        Object roles = claims.get("roles");
        Object permissions = claims.get("permissions");
        log.info("userInfo={},roles={},permissions={}",userInfo,roles,permissions);
    }
}
