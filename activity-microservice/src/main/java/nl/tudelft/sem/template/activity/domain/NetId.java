package nl.tudelft.sem.template.activity.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NetId {
    long netId;

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
