package nl.tudelft.sem.template.user.domain.exceptions;

import nl.tudelft.sem.template.user.domain.NetId;

/**
 * Exception to show the competition with the given netId is not found.
 */
public class UserNotFoundException extends Exception {
    static final long serialVersionUID = -3387516993124229948L;

    public UserNotFoundException(NetId netId) {
        super(netId.toString());
    }
}