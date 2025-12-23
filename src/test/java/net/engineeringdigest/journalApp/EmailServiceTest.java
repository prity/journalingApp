package net.engineeringdigest.journalApp;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.service.EmailService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class EmailServiceTest {


    @Autowired
    EmailService emailService;
    @Disabled
    @Test
    public void sendEmailTest()
    {
        emailService.sendEmail("pritybhudolia@gmail.com","test email","This is a test mail from my journal app");
    }
}
