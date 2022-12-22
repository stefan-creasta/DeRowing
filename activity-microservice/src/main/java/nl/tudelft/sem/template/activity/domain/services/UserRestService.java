package nl.tudelft.sem.template.activity.domain.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.template.activity.domain.events.UserAcceptanceEvent;
import nl.tudelft.sem.template.activity.domain.events.UserJoinEvent;
import nl.tudelft.sem.template.activity.domain.exceptions.UnsuccessfulRequestException;
import nl.tudelft.sem.template.activity.models.InformJoinRequestModel;
import nl.tudelft.sem.template.activity.models.UserDataRequestModel;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class UserRestService implements RestService {

    private final transient Environment environment;
    private final transient int port;
    private final transient String url;

    @Autowired
    public UserRestService(Environment environment) {
        this.environment = environment;
        String userport = environment.getProperty("user.port");
        this.url = environment.getProperty("user.url");
        if (url == null || userport == null) {
            System.out.println("No ports and/or url found in the application.properties file");
        }
        this.port = Integer.parseInt(userport);
    }

    public Object deserialize(Object response, Class<?> target) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(response, target);
    }

    public Object performUserRequest(Object model, String path, Class<?> t) throws Exception {
        String url = environment.getProperty("user.url");
        int port = Integer.parseInt(environment.getProperty("user.port"));
        Object genericResponse = RestService.performRequest(model, url, port, path, HttpMethod.POST);
        return (genericResponse != null)
                ? deserialize(genericResponse.toString(), t)
                : null;
    }
}

