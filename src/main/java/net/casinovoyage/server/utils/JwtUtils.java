package net.casinovoyage.server.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.casinovoyage.server.models.UserModel;
import java.util.Date;

public class JwtUtils {

    public static String generateToken(UserModel user){
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("userId", user.getId());

        String secret = EnvUtils.getProperty("JWT_SIGN_SECRET_KEY");
        int expirationTime = Integer.parseInt(EnvUtils.getProperty("JWT_EXPIRATION_TIME"));
        if(secret == null || expirationTime == 0) return "";

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static boolean verifyToken(String token){
        String secret = EnvUtils.getProperty("JWT_SIGN_SECRET_KEY");
        if(secret == null) return false;

        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static String getUsernameFromToken(String token){
        String secret = EnvUtils.getProperty("JWT_SIGN_SECRET_KEY");
        if(secret == null) return "";

        try{
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return claims.getSubject();
        }catch (Exception e){
            return "";
        }
    }

    public static String getUserIdFromToken(String token){
        String secret = EnvUtils.getProperty("JWT_SIGN_SECRET_KEY");
        if(secret == null) return "";

        try{
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return claims.get("userId").toString();
        }catch (Exception e){
            return "";
        }
    }

    public static boolean isTokenExpired(String token){
        String secret = EnvUtils.getProperty("JWT_SIGN_SECRET_KEY");
        if(secret == null) return true;

        try{
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            Date expirationDate = claims.getExpiration();
            return expirationDate.before(new Date(System.currentTimeMillis()));
        }catch (Exception e){
            return true;
        }
    }
}
