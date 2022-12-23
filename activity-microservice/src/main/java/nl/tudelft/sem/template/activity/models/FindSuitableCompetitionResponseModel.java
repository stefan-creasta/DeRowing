package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import java.util.List;

@Data
public class FindSuitableCompetitionResponseModel {
    List<Long> boatId;
}
