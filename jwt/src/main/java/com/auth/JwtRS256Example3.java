package com.auth;

import com.google.common.collect.Lists;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.List;

/**
 * @author : pc
 * @date : 2020/8/25
 */
@Slf4j
public class JwtRS256Example3 {

    public static String salf = "xuan";
    public static long outTime = 1000 * 60 * 60L;

    public static PrivateKey getPrivateKey() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        String url1 = JwtRS256Example3.class.getClassLoader().getResource("").toString();
        byte[] bytes = Files.readAllBytes(new File(url1+"rsa_private_key.pem").toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(spec);
    }

    public static PublicKey getPublicKey() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        String url1 = JwtRS256Example3.class.getClassLoader().getResource("").toString();
        byte[] bytes = Files.readAllBytes(new File(url1+"rsa_public_key.pem").toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);
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

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        PrivateKey privateKey = getPrivateKey();
        PublicKey publicKey = getPublicKey();
        String jwt = createJwt("zhaoxuan", Lists.newArrayList("admin"), Lists.newArrayList("all"),privateKey);
        log.info("jwt:{}",jwt);
        Claims claims = parserUserToken(jwt, publicKey);
        Object userInfo = claims.get("userInfo");
        Object roles = claims.get("roles");
        Object permissions = claims.get("permissions");
        log.info("userInfo={},roles={},permissions={}",userInfo,roles,permissions);
    }
}
