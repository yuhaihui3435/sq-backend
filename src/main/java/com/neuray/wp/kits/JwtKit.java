////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.kits;

import cn.hutool.core.codec.Base64;
import com.neuray.wp.Consts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtKit {

    private static SignatureAlgorithm signatureAlgorithm;
    private static Key secretKey;

    static {
        signatureAlgorithm = SignatureAlgorithm.HS512;
        secretKey = getAppKey();
    }

    public static  String createJWT(String id, Map<String,Object> data, long ttlMillis) {

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now).setClaims(data)
                .signWith(getAppKey(),signatureAlgorithm);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }


    public static Object parseJWTByDatakey(String jwt,String dataKey){
        Claims claims=parseJWT(jwt);
        return claims.get(dataKey);
    }

    public static Claims parseJWT(String jwt) {

        Claims claims = Jwts.parser()
                .setSigningKey(getAppKey())
                .parseClaimsJws(jwt).getBody();
        return claims;
    }



    private static SecretKey getAppKey(){
        String key=Consts.jwtKey;
        byte[] encodedKey=  Base64.decode(key);
        SecretKey secretKey = new SecretKeySpec(encodedKey,signatureAlgorithm.getJcaName());
        return secretKey;
    }


}
