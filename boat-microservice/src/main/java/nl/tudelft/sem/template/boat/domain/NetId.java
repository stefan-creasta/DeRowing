package nl.tudelft.sem.template.boat.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class NetId implements Serializable {
    public static final long serialVersionUID = 4328743L;
    private String netIdValue;

    public NetId(String netId) {
        this.netIdValue = netId;
    }

    public NetId() {

    }

    public String getNetIdValue() {
        return netIdValue;
    }

    public void setNetIdValue(String netIdValue) {
        this.netIdValue = netIdValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NetId)) {
            return false;
        }
        NetId netId1 = (NetId) o;
        return netIdValue.equals(netId1.netIdValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(netIdValue);
    }
}
