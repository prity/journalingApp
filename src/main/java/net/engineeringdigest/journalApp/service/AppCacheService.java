package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.EntryConfig;
import net.engineeringdigest.journalApp.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppCacheService {

    public enum Keys{
        WEATHER_API
    }
    @Autowired
    private ConfigurationRepository configurationRepository;
    public Map<String,String> AppCache;

    @PostConstruct
    public void init()
    {
        AppCache=new HashMap<>();
        List<EntryConfig> all = configurationRepository.findAll();
        for(EntryConfig entryConfig :all)
        {
            AppCache.put(entryConfig.getKey(),entryConfig.getValue());
        }

    }
}
