package it.uniroma3.domain;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    MeterRegistry meterRegistry;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            meterRegistry.counter("login.failure.count").increment(1);
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            meterRegistry.counter("login.count").increment(1);
            return JwtUserFactory.create(user);
        }
    }
}