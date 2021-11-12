package com.restaurant.backend.security;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.restaurant.backend.domain.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

@Component
public class TokenUtils {

    @Value("sprint-restaurant-application")
    private String APP_NAME;

    @Value("somesecret")
    public String SECRET;

    // 7 days
    @Value("1036800000")
    private long EXPIRES_IN;

    @Value("Authorization")
    private String AUTH_HEADER;

    private static final String AUDIENCE_WEB = "web";

    public static final String USER_TYPE_PASSWORD = "password_user";
    public static final String USER_TYPE_PIN = "pin_user";

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String generateToken(String username) {
        return Jwts.builder().setIssuer(APP_NAME).setSubject(username).setAudience(AUDIENCE_WEB).setIssuedAt(new Date())
                .setExpiration(generateExpirationDate()).signWith(SIGNATURE_ALGORITHM, SECRET).compact();
    }

    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + EXPIRES_IN);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        final String userType = getUserTypeFromToken(token);

        if (userType.equals(USER_TYPE_PASSWORD)) {
            final String username = getUsernameFromToken(token);
            final Date created = getIssuedAtDateFromToken(token);

            return (username != null && username.equals(userDetails.getUsername())
                    && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
        } else if (userType.equals(USER_TYPE_PIN)) {
            // TODO: Get pin from token
            // TODO: Find Staff user with pin
            // TODO: Validate user is not null
            return true;
        }

        return false;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public String getUserTypeFromToken(String token) {
        String userType;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            userType = claims.get("userType").toString();
        } catch (Exception e) {
            userType = null;
        }
        return userType;
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String getToken(HttpServletRequest request) {
        Cookie[] maybeCookies = request.getCookies();
        String cookieName = "accessToken";

        return Optional.ofNullable(maybeCookies).flatMap(
                cookies -> Arrays.stream(cookies).filter(cookie -> cookieName.equals(cookie.getName())).findAny())
                .map(cookie -> cookie.getValue()).orElse(null);
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

}
