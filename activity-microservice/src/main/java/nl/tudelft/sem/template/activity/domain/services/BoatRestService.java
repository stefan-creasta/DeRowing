package nl.tudelft.sem.template.activity.domain.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Activity;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.events.BoatChangeEvent;
import nl.tudelft.sem.template.activity.domain.exceptions.UnsuccessfulRequestException;
import nl.tudelft.sem.template.activity.models.BoatDeleteModel;
import nl.tudelft.sem.template.activity.models.CreateBoatModel;
import nl.tudelft.sem.template.activity.models.CreateBoatResponseModel;
import nl.tudelft.sem.template.activity.models.FindSuitableCompetitionModel;
import nl.tudelft.sem.template.activity.models.FindSuitableCompetitionResponseModel;
import nl.tudelft.sem.template.activity.models.UserDataRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoatRestService implements RestService {
    private final transient Environment environment;
    private final transient int port;
    private final transient String url;

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

    public Object deserialize(Object response, Class<?> target) {
        if (target == null) {
             return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(response, target);
    }

    public Object performBoatRequest(Object model, String path, Class<?> t) throws Exception {
        String url = environment.getProperty("boat.url");
        int port = Integer.parseInt(environment.getProperty("boat.port"));
        Object genericResponse = RestService.performRequest(model, url, port, path, HttpMethod.POST);
        return (genericResponse != null)
                ? deserialize(genericResponse.toString(), t)
                : null;

    }
}
