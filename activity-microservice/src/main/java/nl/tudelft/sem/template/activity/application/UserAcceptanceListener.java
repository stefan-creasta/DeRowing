package nl.tudelft.sem.template.activity.application;

import nl.tudelft.sem.template.activity.domain.events.UserAcceptanceEvent;
import nl.tudelft.sem.template.activity.domain.services.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserAcceptanceListener {

    private final transient UserRestService userRestService;

    @Autowired
    public UserAcceptanceListener(UserRestService userRestService) {
        this.userRestService = userRestService;
    }

    @EventListener
    public void onUserAcceptance(UserAcceptanceEvent event) {
        userRestService.informUserOfRequestUpdate(event);
    }


}
