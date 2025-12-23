package net.engineeringdigest.journalApp.Scheduler;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.service.EmailService;
import net.engineeringdigest.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobScheduler {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;


 //   @Scheduled(cron="0 * */9 ? * SUN")
    public void scheduleMail()
    {
        for (User userOptedForSA : userRepository.getUserForSentimentAnalysis()) {
            if(!userOptedForSA.getJournalentries().isEmpty()) {
                String sentiment = sentimentAnalysisService.getSentiment(userOptedForSA.getJournalentries());
                if (sentiment != null) {
                    emailService.sendEmail(userOptedForSA.getEmail(), "Your weekly sentiment analysis report", "Your overall sentiment was " + sentiment);
                }
            }
        }


    }
}
