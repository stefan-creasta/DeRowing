package nl.tudelft.sem.template.user.domain.models;

import lombok.Data;
import nl.tudelft.sem.template.user.domain.NetId;

@Data
public class UserFindModel {
    private NetId netId;
}
