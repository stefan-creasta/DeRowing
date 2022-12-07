package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import nl.tudelft.sem.template.activity.domain.Gender;

@Data
public class CompetitionFindModel {
    private String competitionName;
    private Position preferredPosition;
    private Gender gender;
    private long startTime;
}
