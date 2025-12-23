package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SentimentAnalysisService {

    public String getSentiment(List<JournalEntry> journalEntries)
    {
        List<Sentiment> sentimentForLast7Days = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());

        Map<Sentiment, Integer> sentimentIntegerMap=new HashMap<>();
        for(Sentiment sentiment:sentimentForLast7Days)
        {
            if(sentiment!=null)
                sentimentIntegerMap.put(sentiment,sentimentIntegerMap.getOrDefault(sentiment,0)+1);
        }
        Map.Entry<Sentiment, Integer> sentimentIntegerEntry = sentimentIntegerMap.entrySet().stream().max((x, y) -> Integer.compare(x.getValue(), y.getValue())).orElse(null);
        return sentimentIntegerEntry!=null?sentimentIntegerEntry.getKey().toString():null;

    }
}
