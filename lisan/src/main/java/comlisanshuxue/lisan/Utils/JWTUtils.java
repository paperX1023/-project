package comlisanshuxue.lisan.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import static javax.crypto.Cipher.SECRET_KEY;

public class JWTUtils {

    // 生成符合 HS512 要求的 512 位密钥（推荐）

    private static final SecretKey KEY = Keys.hmacShaKeyFor(
            Decoders.BASE64.decode("aeFe24txlvh0J8dF9p0/4g5xYn+/UL5huh4cUo3tKx51zZpOETHnkTpJLjnlwVwi5mYUxOajH3lREg90BDf0ew==")
    );

    //获取JWT
    public static String getToken(String username,Integer IsTeacher)  {
        return  Jwts.builder()
                .claim("username", username)
                .claim("IsTeacher", IsTeacher)
                .signWith(KEY,Jwts.SIG.HS512)
                .compact();
    }

    // 验证token  合法性
    public static boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(KEY)
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (Exception e) {
            System.out.println("无效的token：" + authToken);
        }
        return false;
    }

    public static String getUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("username", String.class);
    }
}
