package nl.tudelft.sem.template.activity.application;

import nl.tudelft.sem.template.activity.domain.events.UserAcceptanceEvent;
import nl.tudelft.sem.template.activity.domain.services.RestServiceFacade;
import nl.tudelft.sem.template.activity.domain.services.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserAcceptanceListener {

    private final transient RestServiceFacade restServiceFacade;

    @Autowired
    public UserAcceptanceListener(RestServiceFacade restServiceFacade) {
        this.restServiceFacade = restServiceFacade;
    }

    /**
     * Event Listener for UserAcceptanceEvent.
     *
     * @param event the event that is triggered when a user accepts a boat.
     */
    @EventListener
    public void onUserAcceptance(UserAcceptanceEvent event) {
        try {
            restServiceFacade.performUserModel(event, "notifications/update", null);
        } catch (Exception e) {
            System.out.println("Error while updating user model");
        }
    }


}
