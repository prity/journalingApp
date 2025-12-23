package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

@Autowired
private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<net.engineeringdigest.journalApp.entity.User> user = userRepository.findByUsername(username);
        if (user.isPresent())
        {
            return User.withUsername(user.get().getUsername())
                    .password(user.get().getPassword())
                    .roles(user.get().getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("User doesn't exist: "+username);

    }
}
