package nl.tudelft.sem.template.activity.application;

import nl.tudelft.sem.template.activity.domain.events.BoatChangeEvent;
import nl.tudelft.sem.template.activity.domain.events.UserJoinEvent;
import nl.tudelft.sem.template.activity.domain.services.BoatRestService;
import nl.tudelft.sem.template.activity.domain.services.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserJoinListener {

    private final transient UserRestService userRestService;

    @Autowired
    public UserJoinListener(UserRestService userRestService) {
        this.userRestService = userRestService;
    }

    /**
     * Event Listener for UserJoinEvent.
     *
     * @param event the event that is triggered when a user joins.
     */
    @EventListener
    public void onUserJoin(UserJoinEvent event) {
        try {
            userRestService.performUserRequest(event, "/join", null);
        } catch (Exception e) {
            System.out.println("User join event failed");
        }
    }
}