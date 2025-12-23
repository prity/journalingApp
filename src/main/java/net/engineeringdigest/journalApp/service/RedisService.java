package net.engineeringdigest.journalApp.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> entityClass)
    {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Object obj = redisTemplate.opsForValue().get(key);
            if(obj!=null)
                return objectMapper.readValue(obj.toString(), entityClass);
        }
        catch (Exception ex)
        {
            log.error("Exception : "+ex);
        }
        return null;
    }

    public void set(String key,Object obj,Long ttl)
    {
        try {
            ObjectMapper objectMapper=new ObjectMapper();
            String json = objectMapper.writeValueAsString(obj);
            redisTemplate.opsForValue().set(key, json, ttl, TimeUnit.SECONDS);
        }
        catch (Exception ex)
        {
            log.error("Exception : ",ex);
        }
    }

}
