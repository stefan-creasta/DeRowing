package nl.tudelft.sem.template.user.domain.models;

import lombok.Data;
import nl.tudelft.sem.template.user.domain.Certificate;
import nl.tudelft.sem.template.user.domain.Gender;

@Data
public class UserDetailModel {
    private Gender gender;
    private String organization;
    private boolean amateur;
    private Certificate certificate;
}
