package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import nl.tudelft.sem.template.activity.domain.Certificate;
import nl.tudelft.sem.template.activity.domain.Gender;

@Data
public class UserDataRequestModel {
    private Gender gender;
    private String organization;
    private boolean amateur;
    private Certificate certificate;
}
