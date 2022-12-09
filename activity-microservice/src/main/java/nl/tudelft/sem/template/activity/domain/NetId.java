package nl.tudelft.sem.template.activity.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class NetId implements Serializable {
    private long netId;

    public NetId(long netId) {
        this.netId = netId;
    }

    public long getNetId() {
        return netId;
    }

    public void setNetId(long netId) {
        this.netId = netId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NetId)) return false;
        NetId netId1 = (NetId) o;
        return netId == netId1.netId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(netId);
    }
}
