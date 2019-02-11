package dojo.spring.boot.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dojo.spring.boot.security.exception.ExpiredTokenException;
import dojo.spring.boot.security.exception.InvalidTokenException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TokenUtils {

    private static final String JWT_ISSUER = "Sora Lee";
    private static final int JWT_EXPIRE_DURATION = 3600 * 1000;

    private static final String JWT_AUTHORITIES_CLAIM = "aut";

    private static final String JWT_SIGN_SECRET = "umiss";
    private static final Algorithm JWT_SIGN_ALGORITHM = Algorithm.HMAC512(JWT_SIGN_SECRET);

    public static String buildToken(UserDetails user) {
        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return JWT.create()
                .withSubject(user.getUsername())
                .withArrayClaim(JWT_AUTHORITIES_CLAIM, authorities.toArray(new String[authorities.size()]))
                .withIssuer(JWT_ISSUER)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_EXPIRE_DURATION))
                .sign(JWT_SIGN_ALGORITHM);
    }

    public static UserDetails parseToken(String token)
            throws ExpiredTokenException, InvalidTokenException {
        try {
            DecodedJWT jwt = JWT.require(JWT_SIGN_ALGORITHM)
                    .withIssuer(JWT_ISSUER)
                    .build()
                    .verify(token);

            String username = jwt.getSubject();
            List<GrantedAuthority> authorities = jwt
                    .getClaim(JWT_AUTHORITIES_CLAIM)
                    .asList(String.class)
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            return new User(username, "", authorities);
        } catch (TokenExpiredException e) {
            throw new ExpiredTokenException("JWT token is expired.", e);
        } catch (Exception e) {
            throw new InvalidTokenException("JWT token is invalid.", e);
        }
    }
}
