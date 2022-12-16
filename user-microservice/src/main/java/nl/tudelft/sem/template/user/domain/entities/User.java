package nl.tudelft.sem.template.user.domain.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.user.domain.Certificate;
import nl.tudelft.sem.template.user.domain.Gender;
import nl.tudelft.sem.template.user.domain.NetId;

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
    private boolean amateur;

    /**
     * Constructor for User.
     *
     * @param netId         the netId of the user
     * @param gender        the gender of the user
     * @param certificate   the certificate of the user
     * @param organization  the organization the user is a part of
     * @param amateur       whether the user is amateur or not
     */
    public User(NetId netId, Gender gender, Certificate certificate, String organization, boolean amateur) {
        this.netId = netId;
        this.gender = gender;
        this.certificate = certificate;
        this.organization = organization;
        this.amateur = amateur;
    }

    public NetId getNetId() {
        return netId;
    }

    public Gender getGender() {
        return gender;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public String getOrganization() {
        return organization;
    }

    public boolean isAmateur() {
        return amateur;
    }

    public void setNetId(NetId netId) {
        this.netId = netId;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setAmateur(boolean amateur) {
        this.amateur = amateur;
    }

    /**
     * A method providing string format information.
     *
     * @return a string containing information about the user
     */
    public String toString() {
        return "Your NetId: " + netId.getNetId() + "\n Gender: " + gender.toString() + "\n Certification: "
                + certificate.toString() +  "\n Organization: "
                + organization + "\n Status: "
                + (amateur ? "Amateur" : "Professional");
    }
}
