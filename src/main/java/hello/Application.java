package hello;


import com.codahale.metrics.Counter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;


@SpringBootApplication
public class Application implements InitializingBean {

    Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    MetricRegistry metricRegistry;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Scheduled(initialDelay = 0L, fixedDelay = 1000L)
    public void mockMetricConsumer() {
//        if (metricRegistry.getCounters().containsKey("testCounter")) {
//            Counter counter = metricRegistry.getCounters().get("testCounter");
//            counter.inc();
//            logger.info("Counter: " + counter.getCount());
//        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        int dummpy = 0;
        // metricRegistry.register("testCounter", new Counter());
        //  JmxReporter.forRegistry(metricRegistry).build().start();
    }
}

