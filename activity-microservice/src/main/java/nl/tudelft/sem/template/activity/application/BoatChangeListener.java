package nl.tudelft.sem.template.activity.application;


import nl.tudelft.sem.template.activity.domain.events.BoatChangeEvent;
import nl.tudelft.sem.template.activity.domain.events.UserAcceptanceEvent;
import nl.tudelft.sem.template.activity.domain.services.BoatRestService;
import nl.tudelft.sem.template.activity.domain.services.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BoatChangeListener {

    private final transient BoatRestService boatRestService;

    @Autowired
    public BoatChangeListener(BoatRestService boatRestService) {
        this.boatRestService = boatRestService;
    }

    @EventListener
    public void onUserAcceptance(BoatChangeEvent event) {
        boatRestService.informBoatOfJoining(event);
    }


}
