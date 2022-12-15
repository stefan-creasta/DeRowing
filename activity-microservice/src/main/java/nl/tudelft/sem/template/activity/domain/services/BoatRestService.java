package nl.tudelft.sem.template.activity.domain.services;

import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.activity.domain.events.BoatChangeEvent;
import nl.tudelft.sem.template.activity.domain.exceptions.UnsuccessfulRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class BoatRestService extends RestService {
    // Here we can create methods, that use the parent class' performRequest() to get information from other microservices

    private final transient Environment environment;

    @Autowired
    public BoatRestService(Environment environment) {
        this.environment = environment;
    }

    public boolean informBoatOfJoining(BoatChangeEvent model) {
        String url = environment.getProperty("boat.url");
        int port = Integer.parseInt(environment.getProperty("boat.port"));
        try {
            performRequest(model, url, port, "/insertuser", HttpMethod.POST);
            return true;
        } catch (UnsuccessfulRequestException e) {
            System.out.println("Boat microservice seems unavailable");
            return false;
        }
    }
}
