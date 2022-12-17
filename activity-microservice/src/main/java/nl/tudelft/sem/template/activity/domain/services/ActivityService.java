package nl.tudelft.sem.template.activity.domain.services;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Activity;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public class ActivityService {

    /** Persists all the changes concerning new accepted attendees in the database.
     *
     * @param model The request body from the request to accept/reject a user.
     * @param repository the repository to persist in.
     * @return if succeeded
     */
    public boolean persistNewActivity(AcceptRequestModel model, JpaRepository repository) {
        if (!model.isAccepted()) {
            return true;
        }
        long activityId = model.getActivityId();
        Optional<Activity> optionalActivity = repository.findById(activityId);
        if (optionalActivity.isEmpty()) {
            return false;
        }
        Activity activity = optionalActivity.get();
        List<NetId> attendees = activity.getAttendees();
        attendees.add(model.getRequestee());
        activity.setAttendees(attendees);
        repository.save(activity);
        return true;
    }

}
