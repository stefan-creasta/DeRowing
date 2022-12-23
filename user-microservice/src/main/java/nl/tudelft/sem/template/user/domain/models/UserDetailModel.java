package nl.tudelft.sem.template.user.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.user.domain.Certificate;
import nl.tudelft.sem.template.user.domain.Gender;

@Data
@NoArgsConstructor
public class UserDetailModel {
    @JsonProperty("gender")
    private Gender gender;
    @JsonProperty("organization")
    private String organization;
    @JsonProperty("amateur")
    private boolean amateur;
    @JsonProperty("certificate")
    private Certificate certificate;

    /**
     * A constructor.
     *
     * @param gender       Gender of the user
     * @param organization Organization of the user
     * @param amateur      Status of the user - true if amateur, false if professional
     * @param certificate  Certification obtained by the user
     */
    public UserDetailModel(Gender gender, String organization, boolean amateur, Certificate certificate) {
        this.gender = gender;
        this.organization = organization;
        this.amateur = amateur;
        this.certificate = certificate;
    }
}
