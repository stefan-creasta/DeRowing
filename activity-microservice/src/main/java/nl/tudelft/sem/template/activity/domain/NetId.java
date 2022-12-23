package nl.tudelft.sem.template.activity.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
public class NetId implements Serializable {
    public static final long serialVersionUID = 4328743L;
    private String netIdValue;

    public NetId(String netId) {
        this.netIdValue = netId;
    }

    public String getNetId() {
        return netIdValue;
    }

    public void setNetId(String netId) {
        this.netIdValue = netId;
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

    @Override
    public String toString() {
        return netIdValue;
    }
}
