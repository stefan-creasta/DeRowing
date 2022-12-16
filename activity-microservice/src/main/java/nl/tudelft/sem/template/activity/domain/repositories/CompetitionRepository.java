package nl.tudelft.sem.template.activity.domain.repositories;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, NetId> {
    /**
     * Find competition by using NetId of the competition owner.
     *
     * @param netId the netId of the activity owner
     * @return an activity which is held by its owner
     */
    Competition findByNetId(NetId netId);

    /**
     * Check the usage of the provided netId.
     *
     * @param netId the netId to be searched
     * @return a boolean value which shows whether the netId is used or not
     */
    boolean existsByNetId(NetId netId);


}