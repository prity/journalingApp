package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public void encodePasswordAndSaveUser(User user) {
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }

    public void saveAdmin(User user) {
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        user.setRoles(Arrays.asList("ADMIN","USER"));
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean deleteUser(String username) {
        Optional<User> user=userRepository.findByUsername(username);
        if(user.isPresent())
        {
            userRepository.deleteById(user.get().getId());
            return true;
        }
        return false;
    }

    public User updateUser(String username, User user) {
        Optional<User> oldUser = userRepository.findByUsername(username);
        if (oldUser.isPresent()) {
            oldUser.get().setPassword(!user.getPassword().isEmpty() ? user.getPassword() : oldUser.get().getPassword());
            encodePasswordAndSaveUser(oldUser.get());
            return oldUser.get();
        }
        return null;
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
