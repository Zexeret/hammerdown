package com.site.hammerdown.security.jwt;

import com.site.hammerdown.security.ApplicationSecurityProperties;
import com.site.hammerdown.security.services.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    private final String jwtSecret;
    private final int jwtExpirationMs;
    private final String jwtCookieName;

    @Autowired
    public JwtUtils(ApplicationSecurityProperties applicationSecurityProperties) {
        this.jwtSecret = applicationSecurityProperties.getJwtSecret();
        this.jwtExpirationMs = applicationSecurityProperties.getJwtExpirationMs();
        this.jwtCookieName = applicationSecurityProperties.getJwtCookieName();
    }

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public ResponseCookie generateJwtCookieFromUserDetails(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
        return generateJwtCookieFromToken(jwt);
    }

    public ResponseCookie generateJwtCookieFromUserName(String username) {
        String jwt = generateTokenFromUsername(username);
        return generateJwtCookieFromToken(jwt);
    }

    public ResponseCookie generateJwtCookieFromToken(String token) {
        ResponseCookie cookie = ResponseCookie.from(jwtCookieName, token)
                .path("/api")
                .maxAge(1 * 60 * 60)
                .httpOnly(false)
                .sameSite("Strict")
                .build();
        return cookie;
    }

    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookieName, null)
                .path("/api")
                .build();
        return cookie;
    }


    public String generateTokenFromUsername(String userName) {
        return Jwts.builder()
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("Validate");
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
