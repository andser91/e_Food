package it.uniroma3.domain;


import it.uniroma3.common.event.DomainEventPublisher;
import it.uniroma3.event.UserCreatedEvent;
import it.uniroma3.event.UserServiceChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    public User create(String username, String password, String firstname, String lastname){
        User user = new User(username, password);
        user.setEnabled(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Authority authorityUser=new Authority();
        authorityUser.setName(AuthorityName.ROLE_USER);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityUser);
        user.setAuthorities(authorities);
        userRepository.save(user);
        //genero l'evento di creazione di un nuovo utente
        UserCreatedEvent event = makeUserCreatedEvent(user.getId(), firstname, lastname);
        domainEventPublisher.publish(event, UserServiceChannel.userServiceChannel);
        return user;
    }

    public User createAmdmin(User user){
        return userRepository.save(user);
    }

    private UserCreatedEvent makeUserCreatedEvent(Long id, String firstname, String lastname){
        return new UserCreatedEvent(id,firstname,lastname);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
