package it.uniroma3.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.micrometer.core.instrument.MeterRegistry;
import it.uniroma3.config.security.JwtTokenUtil;
import it.uniroma3.domain.IUserService;
import it.uniroma3.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;


@RestController
public class LoginController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private IUserService userService;

    @Autowired
    private MeterRegistry meterRegistry;


    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) throws AuthenticationException, JsonProcessingException {

        // Effettuo l autenticazione
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Genero Token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        response.setHeader(tokenHeader,token);
        // Ritorno il token
        meterRegistry.counter("total.login.count").increment();
        return ResponseEntity.ok(new JwtAuthenticationResponse(userDetails.getUsername(),userDetails.getAuthorities()));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request){
        if (userService.findByUsername(request.getUsername()) == null){
            User user = userService.create(request.getUsername(), request.getPassword(), request.getFirstname(), request.getLastname());
            meterRegistry.counter("user.registered.count").increment();
            return new ResponseEntity<>(makeCreateUserResponse(user), HttpStatus.CREATED);
        }
        else {
            meterRegistry.counter("user.registered.failure.count").increment();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private CreateUserResponse makeCreateUserResponse(User user){
        return new CreateUserResponse(user.getId());
    }

}
