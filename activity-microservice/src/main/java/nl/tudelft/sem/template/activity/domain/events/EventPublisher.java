package nl.tudelft.sem.template.activity.domain.events;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.InformJoinRequestModel;
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

    public void publishAcceptance(boolean isAccepted, Position position, NetId eventRequester) {
        UserAcceptanceEvent acceptanceEvent = new UserAcceptanceEvent(isAccepted, position, eventRequester);
        applicationEventPublisher.publishEvent(acceptanceEvent);
    }

    public void publishBoatChange(long boatId, Position position) {
        BoatChangeEvent boatChangeEvent = new BoatChangeEvent(boatId, position);
        applicationEventPublisher.publishEvent(boatChangeEvent);
    }

    public void publishJoining(NetId owner, Position position, long activityId) {
        UserJoinEvent userJoinEvent = new UserJoinEvent(owner, position, activityId);
        applicationEventPublisher.publishEvent(userJoinEvent);
    }
}
