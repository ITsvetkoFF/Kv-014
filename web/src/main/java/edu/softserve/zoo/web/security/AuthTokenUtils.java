package edu.softserve.zoo.web.security;

import edu.softserve.zoo.service.security.AuthUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for maintaining remember-me token.
 *
 * @author Ilya Doroshenko
 */
@Component
public class AuthTokenUtils {

    private final static String CLAIM_USER = "sub";
    private final static String CLAIM_CREATED = "created";

    @Value("${token.secret}")
    private String secret;

    @Value("${token.expiration}")
    private Long expiration;

    public AuthTokenUtils() {
    }

    /**
     * Validates token against given {@link UserDetails}
     * @param token token to validate
     * @param userDetails info about the user token is associated with
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        AuthUserDetails user = (AuthUserDetails) userDetails;
        String username = this.getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !(isExpired(token)));
    }

    /**
     * Extracts username from the specifed token.
     * @param token token to extract username from
     * @return username specified in a token
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * Generates token for specified {@link UserDetails}
     * @param userDetails user info container
     * @return valid token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put(CLAIM_USER, userDetails.getUsername());
        claims.put(CLAIM_CREATED, this.generateCurrentDate());
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(this.generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(generateCurrentDate());
    }

    private Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    private Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

}
