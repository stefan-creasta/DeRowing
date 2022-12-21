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

    /**
     * Sends a HTTP request to the USER microservice to retrieve user data.
     *
     * @return the user data
     */
    public UserDataRequestModel getUserData() {
        String url = environment.getProperty("user.url");
        int port = Integer.parseInt(environment.getProperty("user.port"));
        try {
            Object genericResponse = performRequest(null, url, port, "/getdetails", HttpMethod.GET);
            return (genericResponse != null)
                    ? (UserDataRequestModel) deserialize(genericResponse, UserDataRequestModel.class)
                    : null;
        } catch (UnsuccessfulRequestException e) {
            System.out.println("User microservice seems unavailable");
            return null;
        }
    }

    /**
     * A REST call to the user microservice to inform them of a user joining a boat.
     *
     * @param model inform the user microservice that a user wants to join.
     * @return is successful
     */
    public boolean userJoinRequest(UserJoinEvent model) {
        String url = environment.getProperty("user.url");
        int port = Integer.parseInt(environment.getProperty("user.port"));
        try {
            performRequest(model, url, port, "/join", HttpMethod.POST);
            return true;
        } catch (UnsuccessfulRequestException e) {
            System.out.println("User microservice seems unavailable");
            return false;
        }
    }
}

