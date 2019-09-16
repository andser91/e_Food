package it.uniroma3.commonauth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.stream.Collectors;

public class JWTTokenBroker {

    private static JWTTokenBroker instance;

    private JWTTokenBroker() {
    }

    public static synchronized JWTTokenBroker getInstance(){
        if(instance==null)
            instance=new JWTTokenBroker();
        return instance;
    }

    public String getToken(Authentication auth){
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(auth.getName())
                // Convert to list of strings.
                // This is important because it affects the way we get them back in the Gateway.
                .claim("authorities", auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + JWTConfig.getInstance().getExpiration() * 1000))  // in milliseconds
                .signWith(SignatureAlgorithm.HS512, JWTConfig.getInstance().getSecret().getBytes())
                .compact();
    }


} 
