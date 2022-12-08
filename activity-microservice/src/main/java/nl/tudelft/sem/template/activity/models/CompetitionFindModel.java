package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import nl.tudelft.sem.template.activity.domain.Gender;
import nl.tudelft.sem.template.activity.domain.Position;

@Data
public class CompetitionFindModel {
    private String competitionName;
    private Position preferredPosition;
}
