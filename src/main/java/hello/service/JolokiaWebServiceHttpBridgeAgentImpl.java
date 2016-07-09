package hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JolokiaWebServiceHttpBridgeAgentImpl implements JolokiaWebServiceHttpBridgeAgent {

    @Autowired(required = false)
    RestTemplate restTemplate = new RestTemplate();

    @Override
    public String request(String url) {
        return restTemplate.getForObject(url, String.class);
    }
}
