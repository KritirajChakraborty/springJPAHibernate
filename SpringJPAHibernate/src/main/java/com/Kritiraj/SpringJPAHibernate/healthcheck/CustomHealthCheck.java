package com.Kritiraj.SpringJPAHibernate.healthcheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomHealthCheck implements HealthIndicator {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Health health () {
        String url = "http://localhost:8081/rest/check";
        try {
            String response = restTemplate.getForObject(url,String.class);
            if(response.equals("checked")) {
                return Health.up().withDetail("URL: ", url).withDetail("Response: ", response).build();
            } else {
                return Health.down().withDetail("URL: ", url).withDetail("Response: ", response).build();
            }
        } catch (Exception e) {
            return Health.down().withDetail("Reason: ", "Exception occured during the request").build();
        }
    }

}
