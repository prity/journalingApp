package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Constants.Placeholder;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apikey;

    @Autowired
    private AppCacheService appCacheService;
    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city)
    {
        String api=appCacheService.AppCache.get(AppCacheService.Keys.WEATHER_API.toString());
        if(api!=null) {
            String finalAPI = api.replace(Placeholder.ApiKey, apikey).replace(Placeholder.City, city);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            return response.getBody();
        }
        return null;
    }
}
