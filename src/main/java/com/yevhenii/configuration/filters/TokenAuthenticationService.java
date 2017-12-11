package com.yevhenii.configuration.filters;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TokenAuthenticationService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationService.class);
    final String SECRET = "ThisIsASecret";

    public String getTokenByCreds(String username, String password) throws AuthenticationCredentialsNotFoundException {
        if (username == null || password == null)
            return null;
        User user = (User) userDetailsService.loadUserByUsername(username);
        Map<String, Object> tokenData = new HashMap<String, Object>();
        if (password.equals(user.getPassword())) {
            tokenData.put("clientType", "user");
            tokenData.put("username", user.getUsername());
            tokenData.put("password", user.getPassword());
            tokenData.put("token_create_date", new Date().getTime());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, 100);
            Date expirationDate = calendar.getTime();
            tokenData.put("token_expiration_date", expirationDate);
            JwtBuilder jwtBuilder = Jwts.builder();
            jwtBuilder.setExpiration(expirationDate);
            jwtBuilder.setClaims(tokenData);
            String token = jwtBuilder.signWith(SignatureAlgorithm.HS512, SECRET).compact();
            return token;
        } else {
            throw new AuthenticationCredentialsNotFoundException("Authentication error");
        }
    }

    public User getUserByToken(String token) {
        Map<String, Object> tokenData = (Map) Jwts.parser()
                .setSigningKey(SECRET)
                .parse(token)
                .getBody();

        return new User((String)tokenData.get("username"), (String)tokenData.get("password"), new ArrayList<GrantedAuthority>());
    }
}