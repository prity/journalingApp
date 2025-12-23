package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.AppCacheService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCacheService appCacheService;

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers()
    {
        try{

            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cache-init")
    public ResponseEntity<?> init()
    {
        appCacheService.init();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create-admin-users")
    public void createUser(@RequestBody User user)
    {
        userService.saveAdmin(user);
    }

}
