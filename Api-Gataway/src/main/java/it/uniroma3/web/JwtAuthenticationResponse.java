package it.uniroma3.web;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

@Data
public class JwtAuthenticationResponse implements Serializable{

        private static final long serialVersionUID = 1250166508152483573L;

        private final String username;
        Collection<? extends GrantedAuthority> authorities;


        public JwtAuthenticationResponse(String username, Collection<? extends GrantedAuthority> authorities) {
                this.username = username;
                this.authorities = authorities;
        }
}
