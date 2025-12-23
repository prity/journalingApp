package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.util.ClassUtils.isPresent;
@Slf4j
@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

   // private final static Logger logger= LoggerFactory.getLogger(JournalEntryService.class);

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntryWithUsername(String username, JournalEntry entry) {
        try {

            JournalEntry saved = saveEntry(entry);
            Optional<User> user= userService.getUserByUsername(username);
            user.get().getJournalentries().add(saved);
            userService.saveUser(user.get());
        }
        catch (Exception ex)
        {
            log.error("Error occurred for {} :",username,ex);
            throw new RuntimeException("An exception has occured");
        }

    }


    public JournalEntry saveEntry(JournalEntry entry) {
        return journalEntryRepository.save(entry);

    }

    @Transactional
    public void deleteEntryForUser(String username,ObjectId id) {

        try {
            Optional<User> user = userService.getUserByUsername(username);
            Optional<JournalEntry> entry = journalEntryRepository.findById(id);
            if (entry.isPresent() && user.isPresent()) {
                user.get().getJournalentries().removeIf(x -> x.getId().equals(id));
                userService.saveUser(user.get());
                journalEntryRepository.deleteById(id);
            }
        } catch (RuntimeException e) {
            System.out.println(e);
            throw new RuntimeException("Exception encountered while deleting the given id: "+id+" for the user: "+username);
        }

    }

    @Transactional
    public void deleteAllEntriesForUser(String username) {
        try {
            Optional<User> user = userService.getUserByUsername(username);
            if (user.isPresent()) {
                List<JournalEntry> journalentries = user.get().getJournalentries();
                journalentries.forEach(x -> journalEntryRepository.deleteById(x.getId()));
                user.get().setJournalentries(new ArrayList<>());
                userService.saveUser(user.get());
            }
        } catch (RuntimeException e) {
            System.out.println(e);
            throw new RuntimeException("Exception encountered while deleting the entries for the user: "+username);
        }
    }

    @Transactional
    public JournalEntry updateEntryWithEntryId(ObjectId id,JournalEntry newEntry,String username) {
        try {
            Optional<User> user = userService.getUserByUsername(username);
            if (user.isPresent()) {
                if (user.get().getJournalentries().stream().filter(x -> x.getId().equals(id)).count() > 0) {
                    Optional<JournalEntry> oldEntry = journalEntryRepository.findById(id);
                    if (oldEntry.isPresent()) {
                        oldEntry.get().setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.get().getTitle());
                        oldEntry.get().setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.get().getContent());
                        saveEntry(oldEntry.get());
                        return oldEntry.get();
                    }
                }
            }
        }
        catch (RuntimeException e) {
            System.out.println(e);
            throw new RuntimeException("Exception encountered while deleting the entries for the user: "+username);
        }

        return null;
    }

    public List<JournalEntry> getEntriesByUsername(String username) {

        Optional<User> user= userService.getUserByUsername(username);
        return user.map(User::getJournalentries).orElse(null);

    }


}
