package com.hua.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hua.entity.User;

import java.util.Date;

public class JWTUtil {

    private static final String SECRET = "mysecret";
    private static final long EXPIRATION_TIME = 864000000; // 10 days

    public static String createToken(User user) {
        Date expirationDate=new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        String token= JWT.create()
                .withClaim("username",user.getUsername())
                .withExpiresAt(expirationDate)
                .sign(algorithm);
        return token;
    }
    public static boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            Date expiration=jwt.getExpiresAt();
            if (expiration.before(new Date())) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

}