package bux.trading.bot.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class RestTemplateProvider {

    @Value("${bux.authorization.header}")
    private String authorization;

    @Value("${bux.language.header}")
    private String language;

    public RestTemplate provide() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.add("Authorization", authorization);
            headers.add("Accept-language", language);
            headers.add("Content-Type", "application/json");
            headers.add("Accept", "application/json");
            return execution.execute(request, body);
        }));
        return restTemplate;
    }
}