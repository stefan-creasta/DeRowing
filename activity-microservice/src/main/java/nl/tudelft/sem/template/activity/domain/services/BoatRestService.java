package nl.tudelft.sem.template.activity.domain.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class BoatRestService implements RestService {
    private final transient Environment environment;
    private final transient int port;
    private final transient String url;

    /**
     * Constructor for BoatRestService.
     *
     * @param environment the environment
     */
    @Autowired
    public BoatRestService(Environment environment) {
        this.environment = environment;
        String boatport = environment.getProperty("boat.port");
        this.url = environment.getProperty("boat.url");
        if (url == null || boatport == null) {
            System.out.println("No ports and/or url found in the application.properties file");
        }
        this.port = Integer.parseInt(boatport);
    }

    /**
     * Method to perform a request to the boat microservice.
     *
     * @param response the response in json
     * @param target  the target class
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
     * Method to perform a request to the boat microservice.
     *
     * @param model the model to send
     * @param path the path to send the model to
     * @param t the class to cast the response to
     * @return the response cast to the class t
     * @throws Exception if the request fails
     */
    public Object performBoatRequest(Object model, String path, Class<?> t) throws Exception {
        String url = environment.getProperty("boat.url");
        int port = Integer.parseInt(environment.getProperty("boat.port"));
        Object genericResponse = RestService.performRequest(model, url, port, path, HttpMethod.POST);
        return (genericResponse != null)
                ? deserialize(genericResponse, t)
                : null;
    }
}
