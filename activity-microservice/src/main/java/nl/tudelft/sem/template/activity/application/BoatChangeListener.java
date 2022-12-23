package nl.tudelft.sem.template.activity.application;


import nl.tudelft.sem.template.activity.domain.events.BoatChangeEvent;
import nl.tudelft.sem.template.activity.domain.events.UserAcceptanceEvent;
import nl.tudelft.sem.template.activity.domain.services.BoatRestService;
import nl.tudelft.sem.template.activity.domain.services.RestServiceFacade;
import nl.tudelft.sem.template.activity.domain.services.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BoatChangeListener {

    private final transient RestServiceFacade restServiceFacade;

    @Autowired
    public BoatChangeListener(RestServiceFacade restServiceFacade) {
        this.restServiceFacade = restServiceFacade;
    }

    /**
     * Event Listener for BoatChangeEvent.
     *
     * @param event the event that is triggered when a boat is changed.
     */
    @EventListener
    public void onBoatChange(BoatChangeEvent event) {
        try {
            restServiceFacade.performBoatModel(event, "/boat/insert", null);
        } catch (Exception e) {
            System.out.println("Boat change event failed");
        }
    }


}
