package nl.tudelft.sem.template.user.domain.models;

import lombok.Data;
import nl.tudelft.sem.template.user.domain.Certificate;
import nl.tudelft.sem.template.user.domain.Gender;
import nl.tudelft.sem.template.user.domain.Status;

@Data
public class DetailResponseModel {
    private Gender gender;
    private String organization;
    private Status status;
    private Certificate certificate;
}
