package nl.tudelft.sem.template.activity.domain.exceptions;

import nl.tudelft.sem.template.activity.domain.NetId;

/**
 * Exception to show the competition with the given netId is not found.
 */
public class ActivityNotFoundException extends Exception {
    static final long serialVersionUID = -3387516993124229948L;

    public ActivityNotFoundException(NetId netId) {
        super(netId.toString());
    }
}


