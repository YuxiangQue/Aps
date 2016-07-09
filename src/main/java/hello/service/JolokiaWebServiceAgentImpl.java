package hello.service;

import org.apache.commons.lang3.StringUtils;
import org.jolokia.backend.BackendManager;
import org.jolokia.config.ConfigKey;
import org.jolokia.config.Configuration;
import org.jolokia.http.HttpRequestHandler;
import org.jolokia.restrictor.Restrictor;
import org.jolokia.restrictor.RestrictorFactory;
import org.jolokia.util.LogHandler;
import org.jolokia.util.NetworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JolokiaWebServiceAgentImpl implements JolokiaWebServiceAgent {

    private Logger logger = LoggerFactory.getLogger(JolokiaWebServiceAgentImpl.class);

    // To make things simple, just use the HttpRequestHandler rather than build own one
    private HttpRequestHandler jolokiaHandle;

    public JolokiaWebServiceAgentImpl() {
        setup();
    }

    /**
     * Setup as what AgentServlet does
     */
    private void setup() {
        // config
        Configuration config = new Configuration(ConfigKey.AGENT_ID, NetworkUtil.getAgentId(hashCode(), "webservice"));
        config.updateGlobalConfiguration(Collections.singletonMap(ConfigKey.AGENT_TYPE.getKeyValue(), "webservice"));

        // logHandler
        LogHandler logHandler = new LogHandler() {
            @Override
            public void debug(String message) {
                logger.debug(message);
            }

            @Override
            public void info(String message) {
                logger.info(message);
            }

            @Override
            public void error(String message, Throwable t) {
                logger.info(message, t);
            }
        };

        // restrictor
        Restrictor restrictor = RestrictorFactory.createRestrictor(config, logHandler);
        logHandler.info("Using custom access restriction provided by " + restrictor);

        // backendManager
        BackendManager backendManager = new BackendManager(config, logHandler, restrictor);

        // jolokiaHandle
        jolokiaHandle = new HttpRequestHandler(config, backendManager, logHandler);
    }

    /**
     * @param pathInfo_ "read/java.lang:type=Memory/HeapMemoryUsage"
     * @return
     */
    @Override
    public String mockGET(String pathInfo_, Map<String, String> params_) {
        Map<String, String[]> parameterMap = new HashMap<String, String[]>();
        for (String key : params_.keySet()) {
            parameterMap.put(key, params_.get(key).split(","));
        }
        return jolokiaHandle.handleGetRequest("",
                pathInfo_, parameterMap).toJSONString();
    }


    @Override
    public String read(String mbean_, List<String> attribute_, String path_, Map<String, String> params_) {
        StringBuilder sb = new StringBuilder();
        sb.append("/").append("read")
                .append("/").append(mbean_);
        if (attribute_.size() != 0)
            sb.append("/").append(StringUtils.join(attribute_, ","));
        if (!StringUtils.isEmpty(path_))
            sb.append("/?p=").append(path_);
        return mockGET(sb.toString(), params_);
    }

    @Override
    public String write(String mbean_, String attribute_, String value_, String path_, Map<String, String> params_) {
        StringBuilder sb = new StringBuilder();
        sb.append("/").append("write")
                .append("/").append(mbean_)
                .append("/").append(attribute_)
                .append("/").append(value_);
        if (!StringUtils.isEmpty(path_))
            sb.append("/?p=").append(path_);
        return mockGET(sb.toString(), params_);
    }

    @Override
    public String exec(String mbean_, String operation_, List<String> args_, Map<String, String> params_) {
        StringBuilder sb = new StringBuilder();
        sb.append("/").append("exec")
                .append("/").append(mbean_)
                .append("/").append(operation_);
        if (args_.size() != 0)
            sb.append("/").append(StringUtils.join(args_, "/"));
        return mockGET(sb.toString(), params_);
    }

    @Override
    public String search(String mbean, Map<String, String> params_) {
        StringBuilder sb = new StringBuilder();
        sb.append("/").append("search")
                .append("/").append(mbean);
        return mockGET(sb.toString(), params_);
    }

    @Override
    public String list(String path_, Map<String, String> params_) {
        StringBuilder sb = new StringBuilder();
        sb.append("/").append("list");
        if (!StringUtils.isEmpty(path_))
            sb.append("/?p=").append(path_);
        return mockGET(sb.toString(), params_);
    }
}
