package hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class JolokiaWebServiceHttpBridgeAgentImpl implements JolokiaWebServiceHttpBridgeAgent {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String request(String url) {
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (RestClientException ex) {
            return ex.getMessage();
        }
    }
}
