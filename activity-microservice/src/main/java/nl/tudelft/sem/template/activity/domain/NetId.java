package nl.tudelft.sem.template.activity.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

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
}
