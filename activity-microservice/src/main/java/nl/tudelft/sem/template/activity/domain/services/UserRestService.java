package nl.tudelft.sem.template.activity.domain.services;

import nl.tudelft.sem.template.activity.domain.events.UserAcceptanceEvent;
import nl.tudelft.sem.template.activity.domain.exceptions.UnsuccessfulRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class UserRestService extends RestService {

    private final transient Environment environment;

    @Autowired
    public UserRestService(Environment environment) {
        this.environment = environment;
    }

    /**
     * Sends a HTTP request to the USER microservice to inform them of changes
     * surrounding the acceptance/rejecting of a user.
     *
     * @param model The request body we will be sending.
     * @return is successful
     */
    public boolean informUserOfRequestUpdate(UserAcceptanceEvent model) {
        String url = environment.getProperty("user.url");
        int port = Integer.parseInt(environment.getProperty("user.port"));
        try {
            performRequest(model, url, port, "/update", HttpMethod.POST);
            return true;
        } catch (UnsuccessfulRequestException e) {
            System.out.println("User microservice seems unavailable");
            return false;
        }
    }
}

