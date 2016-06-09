package edu.softserve.zoo.web.security;

import edu.softserve.zoo.service.security.AuthUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

/**
 * Utility class for maintaining remember-me token.
 *
 * @author Ilya Doroshenko
 */
@Component
public class JwtUtils {

    @Value("${token.expiration}")
    private Long expiration;

    //private Key key;

    private String key;

    @PostConstruct
    private void initSecretKey() {
        //key = MacProvider.generateKey();
        key = "123";
    }

    /**
     * Validates token against given {@link UserDetails}
     *
     * @param token       token to validate
     * @param userDetails info about the user token is associated with
     * @return true if token is valid
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        AuthUserDetails user = (AuthUserDetails) userDetails;
        Claims claims = getClaimsFromToken(token);

        String tokenId = claims.getId();
        String username = claims.getSubject();

        return (username.equals(user.getUsername())
                && !(isExpired(token))
                && tokenId.equals(user.getToken()));
    }

    /**
     * Extracts username from the specified token.
     *
     * @param token token to extract username from
     * @return username specified in a token
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * Extracts token ID from the specified token.
     *
     * @param token token to extract ID from
     * @return ID of a token
     */
    public String getIdFromToken(String token) {
        String id;
        try {
            Claims claims = getClaimsFromToken(token);
            id = claims.getId();
        } catch (Exception e) {
            id = null;
        }
        return id;
    }

    /**
     * Generates token for specified {@link UserDetails}
     *
     * @param userDetails user info container
     * @return valid token
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(generateCurrentDate())
                .setExpiration(generateExpirationDate())
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
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
            created = claims.getIssuedAt();
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
