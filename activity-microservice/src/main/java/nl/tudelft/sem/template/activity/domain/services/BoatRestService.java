package nl.tudelft.sem.template.activity.domain.services;

import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.events.BoatChangeEvent;
import nl.tudelft.sem.template.activity.domain.exceptions.UnsuccessfulRequestException;
import nl.tudelft.sem.template.activity.models.CreateBoatModel;
import nl.tudelft.sem.template.activity.models.CreateBoatResponseModel;
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


    /**
     * A method to inform the boat service of an extra user.
     *
     * @param model the model to insert a user into a boat
     * @return boolean whether the request was successful
     */
    public boolean informBoatOfJoining(BoatChangeEvent model) {
        String url = environment.getProperty("boat.url");
        int port = Integer.parseInt(environment.getProperty("boat.port"));
        try {
            performRequest(model, url, port, "/boat/insert", HttpMethod.POST);
            return true;
        } catch (UnsuccessfulRequestException e) {
            System.out.println("Boat microservice seems unavailable");
            return false;
        }
    }

    /**
     * Tells the boat service to create a new boat.
     *
     * @param type the type of boat to create
     * @param numPeople the number of people the boat can hold
     * @return the boat id of the newly creted boat.
     */
    public long getBoatId(Type type, int numPeople) {
        String url = environment.getProperty("boat.url");
        int port = Integer.parseInt(environment.getProperty("boat.port"));
        CreateBoatModel model = new CreateBoatModel();
        model.setType(type);
        model.setNumPeople(numPeople);
        try {
            CreateBoatResponseModel response =
                    (CreateBoatResponseModel)
                            performRequest(model, url, port, "/boat/create", HttpMethod.POST);
            if (response == null) {
                return -1;
            }
            return response.getBoatId();
        } catch (UnsuccessfulRequestException e) {
            System.out.println("Boat microservice seems unavailable");
            return -1;
        }
    }

    /**
     * The method to delete a boat when delete an activity.
     *
     * @param boatId the id of the boat to be deleted
     * @return a boolean value representing the result
     */
    public boolean deleteBoat(long boatId) {
        String url = environment.getProperty("boat.url");
        int port = Integer.parseInt(environment.getProperty("boat.port"));
        try {
            performRequest(boatId, url, port, "/boat/delete", HttpMethod.POST);
            return true;
        } catch (UnsuccessfulRequestException e) {
            System.out.println("Boat microservice seems unavailable");
            return false;
        }
    }
}
