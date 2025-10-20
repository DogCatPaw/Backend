//package kpaas.dogcat.global.jwt;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//
//@Component
//public class JwtVerifier {
//
//    private final SecretKey key;
//
//    public JwtVerifier(@Value("${jwt.secret}") String secretKey) {
//        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//    }
//
//    /** JWT 유효성 검증 */
//    public boolean isValid(String token) {
//        try {
//            Jwts.parser()
//                    .verifyWith(key)
//                    .build()
//                    .parseSignedClaims(token);
//            return true;
//        } catch (JwtException e) {
//            return false;
//        }
//    }
//
//    /** Claims 추출 */
//    public Claims parse(String token) {
//        return Jwts.parser()
//                .verifyWith(key)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//
//    /** walletAddress (address claim) 추출 */
//    public String getWalletAddress(String token) {
//        return parse(token).get("address", String.class);
//    }
//
//    /** 만료 여부 */
//    public boolean isExpired(String token) {
//        Date exp = parse(token).getExpiration();
//        return exp.before(new Date());
//    }
//}