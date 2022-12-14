package nl.tudelft.sem.template.activity.domain.events;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

    private final transient ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishAcceptance(boolean isAccepted, NetId eventOwner, NetId eventRequester) {
        UserAcceptanceEvent acceptanceEvent = new UserAcceptanceEvent(isAccepted, eventOwner, eventRequester);
        applicationEventPublisher.publishEvent(acceptanceEvent);
    }
}
