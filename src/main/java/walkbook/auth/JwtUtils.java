package walkbook.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${JwtSecretKey}")
    public String SECRET_KEY;
    public final int TOKEN_EXPIRED_TIME = 60_000 * 30;

    public String createJwt(String username) {
        return JWT.create()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRED_TIME))
                .withClaim("username", username)
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    public boolean isInvalid(String authHeader) {
        if (authHeader == null)
            return true;
        if (!authHeader.startsWith("Bearer"))
            return true;
        return false;
    }

    public String getUsernameFromJwt(String jwt) {
        return JWT.require(Algorithm.HMAC512(SECRET_KEY))
                .build()
                .verify(jwt)
                .getClaim("username").asString();
    }
}
