package org.aleksa.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class JwtUtil {

    private static RSAKey publicKey;
    private static RSAKey privateKey;
    
    // Token expiration times
    private static final int ACCESS_TOKEN_EXPIRATION = 3600; // 1 hour in seconds
    private static final int REFRESH_TOKEN_EXPIRATION = 2592000; // 30 days in seconds

    /**
     * Create a standard access JWT token
     */
    public static String createJWT(String subject, Map<String, Object> claims) {
        if(!isLoaded())
            loadKeys();

        try {
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);
            return JWT.create()
                    .withIssuer("cinema-API")
                    .withSubject(subject)
                    .withExpiresAt(Date.from(Instant.now().plusSeconds(ACCESS_TOKEN_EXPIRATION)))
                    .withIssuedAt(Date.from(Instant.now()))
                    .withClaim("claims", claims)
                    .withJWTId(UUID.randomUUID().toString())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException(exception);
        }
    }
    
    /**
     * Create a refresh token with longer expiration
     */
    public static String createRefreshToken(String subject) {
        if(!isLoaded())
            loadKeys();

        try {
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);
            return JWT.create()
                    .withIssuer("cinema-API")
                    .withSubject(subject)
                    .withExpiresAt(Date.from(Instant.now().plusSeconds(REFRESH_TOKEN_EXPIRATION)))
                    .withIssuedAt(Date.from(Instant.now()))
                    .withClaim("type", "refresh")
                    .withJWTId(UUID.randomUUID().toString())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException(exception);
        }
    }
    
    /**
     * Refresh an access token using a valid refresh token
     */
    public static String refreshToken(String refreshToken, Map<String, Object> claims) {
        DecodedJWT decodedRefreshToken = decodeJWT(refreshToken);
        
        if (decodedRefreshToken == null) {
            throw new JWTVerificationException("Invalid refresh token");
        }
        
        // Check if it's actually a refresh token
        String tokenType = decodedRefreshToken.getClaim("type").asString();
        if (!"refresh".equals(tokenType)) {
            throw new JWTVerificationException("Not a refresh token");
        }
        
        // Create a new access token
        return createJWT(decodedRefreshToken.getSubject(), claims);
    }

    /**
     * Decode and verify a JWT token
     */
    public static DecodedJWT decodeJWT(String token) {
        if(!isLoaded())
            loadKeys();

        try {
            DecodedJWT decodedJWT;
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("cinema-API")
                    .build();
            decodedJWT = verifier.verify(token);
            return decodedJWT;
        } catch (JWTVerificationException exception){
            return null;
        }
    }
    
    /**
     * Check if a token is about to expire (within the next 5 minutes)
     */
    public static boolean isTokenAboutToExpire(String token) {
        DecodedJWT jwt = decodeJWT(token);
        if (jwt == null) {
            return true;
        }
        
        // Check if token expires within the next 5 minutes
        Instant expiresAt = jwt.getExpiresAt().toInstant();
        Instant fiveMinutesFromNow = Instant.now().plusSeconds(300); // 5 minutes
        
        return expiresAt.isBefore(fiveMinutesFromNow);
    }

    private static void loadKeys(){
        try {
            String pubString = Files.readString(Path.of("keys/public.key"), Charset.defaultCharset());
            String privString = Files.readString(Path.of("keys/private.key"), Charset.defaultCharset());

            publicKey = (RSAKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(normalisePublicKey(pubString)));
            privateKey = (RSAKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(normalisePrivateKey(privString)));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] normalisePublicKey(String base64EncodedKey) {
        base64EncodedKey = base64EncodedKey
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("\n", "")
                .replace("-----END PUBLIC KEY-----", "");
       return Base64.getDecoder().decode(base64EncodedKey);
    }

    private static byte[] normalisePrivateKey(String base64EncodedKey) {
        base64EncodedKey = base64EncodedKey
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll("\n", "")
                .replace("-----END PRIVATE KEY-----", "");
        return Base64.getDecoder().decode(base64EncodedKey);
    }

    private static boolean isLoaded(){
        return publicKey != null && privateKey != null;
    }
}
