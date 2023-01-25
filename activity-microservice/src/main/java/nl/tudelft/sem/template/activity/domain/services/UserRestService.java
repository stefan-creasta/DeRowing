package nl.tudelft.sem.template.activity.domain.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.template.activity.domain.exceptions.UnsuccessfulRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class UserRestService implements RestService {

    private final transient Environment environment;
    private final transient String portString;
    private final transient int port;
    private final transient String url;

    /**
     * Constructor for UserRestService.
     *
     * @param environment the environment
     */
    @Autowired
    public UserRestService(Environment environment) throws UnsuccessfulRequestException {
        this.environment = environment;
        this.portString = environment.getProperty("user.port");
        this.url = environment.getProperty("user.url");
        if (url == null || portString == null) {
            throw new UnsuccessfulRequestException();
        }
        this.port = Integer.parseInt(portString);
    }

    public String getPortString() {
        return this.portString;
    }

    public String getUrl() {
        return this.url;
    }

    /**
     * Method to perform a request to the user microservice.
     *
     * @param response the response in json
     * @param target the target class
     * @return the object cast to target class
     */
    public Object deserialize(Object response, Class<?> target) {
        if (target == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(response, target);
    }

    /**
     * Method to perform a request to the user microservice.
     *
     * @param model the model to send
     * @param path the path to send the model to
     * @param t the class to cast the response to
     * @return the response cast to the class t
     * @throws Exception if the request fails
     */
    public Object performUserRequest(Object model, String path, Class<?> t) throws Exception {
        Object genericResponse = RestService.performRequest(model, url, port, path, HttpMethod.POST);
        return (genericResponse != null)
                ? deserialize(genericResponse, t)
                : null;
    }
}

