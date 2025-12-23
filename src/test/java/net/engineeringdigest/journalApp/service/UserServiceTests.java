package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserService userService;


    @ParameterizedTest
    @ValueSource(strings={ "hiuhbkj","sahil","dileep","mohan" })
    public void findByUsername(String name)
    {
        Optional<User> user = userService.getUserByUsername(name);
        assertTrue(user.isPresent());
        if(user.isPresent())
            assertTrue(!user.get().getJournalentries().isEmpty());
    }




    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,3,6",
            "3,3,6",
            "4,6,10",
            "5,5,10"
    })
    public void calculate(int a, int b, int expected)
    {
        assertEquals(expected,a,b);
    }

}
