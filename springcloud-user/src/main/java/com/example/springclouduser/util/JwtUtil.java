package com.example.springclouduser.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @Description: JWT生成解析工具类
 * @Author: Caolele
 * @Date: 07-11-2022 周一 9:09
 */

@Slf4j
public class JwtUtil {
    public static final Long JWT_TTL = 60 * 60 * 1000 * 24L;//一个小时
    public static final String JWT_KEY = "mySecretKeyForJwtSigningMustBeAtLeast32BytesLong123456";//秘钥明文


    public static String getUUID() {
        String token = UUID.randomUUID().toString().replace("-", "");
        return token;
    }

    public static String createJWT(String subject,Map<String,Object> claims) {
        JwtBuilder builder = getJWTBuilder(subject, null, getUUID(),claims);
        return builder.compact();
    }



    private static JwtBuilder getJWTBuilder(String subject, Long ttlMillis, String uuid,Map<String,Object> claims) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMills = System.currentTimeMillis();
        Date now = new Date(nowMills);
        if (ttlMillis == null)
            ttlMillis = JwtUtil.JWT_TTL;
        long expMillis = nowMills + ttlMillis;
        Date expireDate = new Date(expMillis);

        return Jwts.builder()
                .addClaims(claims)
                .setId(uuid)//唯一的id
                .setSubject(subject)//可以是JSON数据
                .setIssuer("sg")//签发者
                .setIssuedAt(now)//签发时间
                .signWith(signatureAlgorithm, secretKey)//算法，秘钥
                .setExpiration(expireDate);
    }

    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA256");
        return key;

    }


    /**
     * @Description: 解析jwt
     * @Param: [jwt]
     * @Return: io.jsonwebtoken.Claims
     * @DateTime: 9:38 2022/7/11
     */

    public static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    public static Map<String,Object> getClaims(String token) {
        Claims claims = parseJWT(token);
        return claims;
    }
}
