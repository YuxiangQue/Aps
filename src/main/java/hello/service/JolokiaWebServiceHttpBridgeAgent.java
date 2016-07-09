package hello.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Bridge http agent to web service
 *
 * @author yuxiangque
 * @version 2016/7/9
 */

@WebService(targetNamespace = JolokiaWebServiceHttpBridgeAgent.NS_URL, name = JolokiaWebServiceHttpBridgeAgent.NAME)
public interface JolokiaWebServiceHttpBridgeAgent {
    String NS_URL = "jolokia.agent.webservice";
    String NAME = "JolokiaWebServiceHttpBridgeAgent";

    @WebMethod
    String request(@WebParam(name = "url") String url);
}

