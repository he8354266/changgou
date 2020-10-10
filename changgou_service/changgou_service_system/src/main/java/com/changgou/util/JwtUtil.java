package com.changgou.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/9/2418:59
 */
public class JwtUtil {
    //有效期
    public static final Long JWT_TTL = 3600000L;// 60 * 60 *1000  一个小时
    //设置密钥明文
    public static final String JWT_KET = "itcast";

    public static String createJWT(String id, String subject, Long ttlMills) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //获取当前时间
        Long nowMills = System.currentTimeMillis();
        Date now = new Date(nowMills);
        if (ttlMills == null) {
            ttlMills = JWT_TTL;
        }
        long expMillis = nowMills + ttlMills;
        Date expDate = new Date(expMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject) // 主题  可以是JSON数据
                .setIssuer("admin") // 签发者
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, secretKey)
                .setExpiration(expDate);
        return builder.compact();
    }

    private static SecretKey generalKey() {
        byte[] encodeKey = Base64.getDecoder().decode(JWT_KET);
        SecretKey key = new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
        return key;
    }
}
