package edu.softserve.zoo.web.security;

import edu.softserve.zoo.service.security.AuthUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

/**
 * Utility class for maintaining remember-me token.
 *
 * @author Ilya Doroshenko
 */
@Component
public class JwtUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${token.expiration}")
    private Long expiration;

    private Key key;

    public JwtUtils() {
        key = MacProvider.generateKey();
    }

    /**
     * Validates token against given {@link UserDetails}
     *
     * @param token token to validate
     * @param user  info about the user token is associated with
     * @return true if token is valid
     */
    public Boolean validateToken(String token, AuthUserDetails user) {
        Claims claims = getClaimsFromToken(token);

        String tokenId = claims.getId();
        String username = claims.getSubject();

        return (username.equals(user.getUsername())
                && !(isExpired(token))
                && tokenIssuedAfterLastPasswordChange(token, user)
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
            LOGGER.debug("Failed to extract username from token");
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
            LOGGER.debug("Failed to extract id from token.");
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
            LOGGER.warn("Failed to extract claims from token. Reason: {}", e.getMessage());
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
            LOGGER.debug("Failed to extract created date from token");
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
            LOGGER.debug("Failed to extract expiration date from token");
        }
        return expiration;
    }

    private Boolean tokenIssuedAfterLastPasswordChange(String token, AuthUserDetails userDetails) {
        LocalDateTime dateTime = userDetails.getPasswordChangeDate();
        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        return getCreatedDateFromToken(token).after(date);
    }

}
