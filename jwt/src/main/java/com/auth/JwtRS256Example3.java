package com.auth;

import com.google.common.collect.Lists;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
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

    public static long outTime = 1000 * 60 * 60L;

    public static String getPrivateKey() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        InputStream inputStream = JwtRS256Example3.class.getResourceAsStream("/rsa_private_key.pem");
        String builderStr = getKey(inputStream);
        return builderStr.replaceAll("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll("-----END PRIVATE KEY-----", "")
                .replaceAll("\r", "")
                .replaceAll("\n", "")
                .replaceAll("\r\n", "");
    }

    private static String getKey(InputStream inputStream) throws IOException {
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        StringBuilder builder = new StringBuilder();
        int n;
        while ((n = reader.read()) != -1) {
            builder.append((char) n);
        }
        return builder.toString();
    }

    public static String getPublicKey() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        InputStream inputStream = JwtRS256Example3.class.getResourceAsStream("/rsa_public_key.pem");
        String builderStr = getKey(inputStream);
        return builderStr.replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("\r", "")
                .replaceAll("\n", "")
                .replaceAll("\r\n", "");
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

    public static void main(String[] args) throws Exception {
        String privateKey = getPrivateKey();
        String publicKey = getPublicKey();
        String jwt = createJwt("zhaoxuan", Lists.newArrayList("admin"), Lists.newArrayList("all"),RSAUtils.getPrivateKey(privateKey));
        log.info("jwt:{}",jwt);
        Claims claims = parserUserToken(jwt, RSAUtils.getPublicKey(publicKey));
        Object userInfo = claims.get("userInfo");
        Object roles = claims.get("roles");
        Object permissions = claims.get("permissions");
        log.info("userInfo={},roles={},permissions={}",userInfo,roles,permissions);
    }
}
