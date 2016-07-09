package hello.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
import java.util.Map;

/**
 * https://jolokia.org/reference/html/protocol.html
 */
@WebService(targetNamespace = JolokiaWebServiceAgent.NS_URL, name = JolokiaWebServiceAgent.NAME)
public interface JolokiaWebServiceAgent {
    String NS_URL = "jolokia.agent.webservice";
    String NAME = "JolokiaWebServiceAgent";

    /**
     * @param pathInfo_
     * @return
     */
    @WebMethod
    String mockGET(@WebParam(name = "pathInfo") String pathInfo_,
                   @WebParam(name = "parameterMap") Map<String, String> params_);


    /**
     * Specific attribute will be requested
     *
     * @param mbean_     java.lang:type=Memory
     * @param attribute_ HeapMemoryUsage
     * @param path       used
     * @return
     */
    @WebMethod
    String read(@WebParam(name = "mbean") String mbean_,
                @WebParam(name = "attribute") List<String> attribute_,
                @WebParam(name = "path") String path,
                @WebParam(name = "params") Map<String, String> params_);


    /**
     * @param mbean_     java.lang:type=ClassLoading
     * @param attribute_ Verbose
     * @param value_     true
     * @param path
     * @return
     */
    @WebMethod
    String write(@WebParam(name = "mbean") String mbean_,
                 @WebParam(name = "attribute") String attribute_,
                 @WebParam(name = "value") String value_,
                 @WebParam(name = "path") String path,
                 @WebParam(name = "params") Map<String, String> params_);


    /**
     * @param mbean_     java.lang:type=Threading
     * @param operation_ dumpAllThreads
     * @param args_      [true,true]
     * @return
     */
    @WebMethod
    String exec(@WebParam(name = "mbean") String mbean_,
                @WebParam(name = "operation") String operation_,
                @WebParam(name = "args") List<String> args_,
                @WebParam(name = "params") Map<String, String> params_);


    /**
     * @param mbean java.lang:*
     * @return
     */
    @WebMethod
    String search(@WebParam(name = "mbean") String mbean,
                  @WebParam(name = "params") Map<String, String> params_);

    /**
     * @param path_ java.lang/type=Memory/attr
     * @return
     */
    @WebMethod
    String list(@WebParam(name = "path") String path_,
                @WebParam(name = "params") Map<String, String> params_);
}
