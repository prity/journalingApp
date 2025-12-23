package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        List<JournalEntry> allEntries = journalEntryService.getEntriesByUsername(username);
        if(allEntries!=null && !allEntries.isEmpty())
            return new ResponseEntity<>(allEntries,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> create(@RequestBody JournalEntry newEntry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        try{
            journalEntryService.saveEntryWithUsername(username,newEntry);
            return new ResponseEntity<>(newEntry,HttpStatus.CREATED);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<JournalEntry> update(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        JournalEntry updatedEntry= journalEntryService.updateEntryWithEntryId(myId,newEntry,username);
        if(updatedEntry!=null)
            return new ResponseEntity<>(updatedEntry, HttpStatus.ACCEPTED);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        try {
            journalEntryService.deleteAllEntriesForUser(username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId)
    {
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            journalEntryService.deleteEntryForUser(authentication.getName(),myId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

 }
