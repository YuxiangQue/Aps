package hello;

import hello.service.JolokiaWebServiceAgent;
import hello.service.JolokiaWebServiceHttpBridgeAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.mvc.JolokiaMvcEndpoint;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.MalformedObjectNameException;
import java.util.Arrays;
import java.util.Collections;

@Controller
@EnableAutoConfiguration
public class SampleController {

    @Autowired
    JolokiaMvcEndpoint endpoint;
    @Autowired
    JolokiaWebServiceAgent serviceAgent;
    @Autowired
    JolokiaWebServiceHttpBridgeAgent serviceHttpBridgeAgent;
    private Logger logger = LoggerFactory.getLogger(SampleController.class);
    @Value("${server.port}")
    private String serverPort;
    private String serverAddress = "localhost";

    @RequestMapping("/")
    @ResponseBody
    String home() throws MalformedObjectNameException {
        String fullURL = "http://" + serverAddress + ":" + serverPort + "/jolokia/read/java.lang:type=Memory/HeapMemoryUsage";
        serviceHttpBridgeAgent.request(fullURL);
        logger.info(serviceAgent.read(
                "java.lang:type=Memory",
                Arrays.asList("HeapMemoryUsage", "ObjectName"),
                "",
                Collections.<String, String>emptyMap()));
        logger.info(serviceAgent.mockGET(
                "read/java.lang:type=Memory/HeapMemoryUsage,ObjectName",
                Collections.<String, String>emptyMap()));
        return serviceAgent.list("", Collections.<String, String>emptyMap());
    }
}

