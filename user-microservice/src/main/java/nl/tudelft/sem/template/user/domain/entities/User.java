package nl.tudelft.sem.template.user.domain.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.user.domain.Certificate;
import nl.tudelft.sem.template.user.domain.Gender;
import nl.tudelft.sem.template.user.domain.NetId;
import java.util.Objects;

@NoArgsConstructor
@Entity
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

    /**
     * Compare two Users.
     *
     * @param o the other user
     * @return true if two users are equal and otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User other = (User) o;
        return netId.equals(other.netId) && gender.equals(other.gender)
            && certificate.equals(other.certificate) && organization.equals(other.organization) && amateur == other.amateur;
    }


    /**
     * Hash the object.
     *
     * @return the hashcode of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(getNetId(), getGender(), getCertificate(), getOrganization(), isAmateur());
    }
}
