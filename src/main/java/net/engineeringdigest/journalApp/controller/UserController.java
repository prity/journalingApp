package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;


    @PutMapping
    public ResponseEntity<User> edit(@RequestBody User newEntry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User updatedEntry= userService.updateUser(userName,newEntry);
        if(updatedEntry!=null)
            return new ResponseEntity<>(updatedEntry, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity<?> delete()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        if(userService.deleteUser(userName))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping
    public ResponseEntity<?> Greeting()
    {
     Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weather = weatherService.getWeather("Mumbai");
        String greeting="Hi "+authentication.getName() +" ..Today's weather feels like "+weather.getCurrent().getFeelsLike();
        return new ResponseEntity<>(greeting,HttpStatus.OK);
    }



}
