package com.caiodorn.codingtests.backbase.api.authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;

@Slf4j
public class TokenAuthenticator {

    private static final long EXPIRATION_TIME = 3600000;
    private static final String SECRET = "MySecret";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";

    protected static void addAuthentication(HttpServletResponse response, String username) {
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + generateJwtToken(username));
    }

    protected static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        Authentication authentication = null;

        try {
            if (token != null) {
                String user = authenticateToken(token);

                if (user != null) {
                    authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                }
            }
        } catch (Exception e) {
            log.error("An error happened while attempting to parse JWT token... \nToken: {}", token, e);
        }

        return authentication;
    }

    private static String authenticateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
    }

    private static String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

}
