package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserDetailsServiceImplsTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void loadUserByUsernameTest(){
        //creating mock object to avoid fetching the user record from the database
        User user=new User();
        user.setUsername("praveen");
        user.setPassword("praveen");
        user.setRoles(new ArrayList<>(Arrays.asList("USER")));

        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(user));
        UserDetails userDetails=userDetailsService.loadUserByUsername("sahil");
        assertNotNull(userDetails);
    }

}
