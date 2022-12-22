package nl.tudelft.sem.template.activity.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestServiceFacade {
    private final transient UserRestService userRestService;
    private final transient BoatRestService boatRestService;

    @Autowired
    public RestServiceFacade(UserRestService userRestService, BoatRestService boatRestService) {
        this.boatRestService = boatRestService;
        this.userRestService = userRestService;
    }

    public Object performUserModel(Object model, String path, Class t) throws Exception {
        return userRestService.performUserRequest(model, path, t);
    }

    public Object performBoatModel(Object model, String path, Class t) throws Exception {
        return boatRestService.performBoatRequest(model, path, t);
    }
}
