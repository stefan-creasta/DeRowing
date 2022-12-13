package nl.tudelft.sem.template.user.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
public class User {

    @Id
    @EmbeddedId
    private NetId netId;
    @Column
    private Gender gender;
    @Column
    private Certificate certificate;
    @Column
    private String organization;
    @Column
    private Status status;

    /**
     * Constructor for User.
     *
     * @param netId         the netId of the user
     * @param gender        the gender of the user
     * @param certificate   the certificate of the user
     * @param organization  the organization the user is a part of
     * @param status        the status of the user (amateur/professional)
     */
    public User(NetId netId, Gender gender, Certificate certificate, String organization, Status status) {
        this.netId = netId;
        this.gender = gender;
        this.certificate = certificate;
        this.organization = organization;
        this.status = status;
    }
}
