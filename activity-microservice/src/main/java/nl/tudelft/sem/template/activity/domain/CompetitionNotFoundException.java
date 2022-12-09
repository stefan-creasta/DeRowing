package nl.tudelft.sem.template.activity.domain;

/**
 * Exception to show the competition with the given netId is not found.
 */
public class CompetitionNotFoundException extends Exception {
    static final long serialVersionUID = -3387516993124229948L;

    public CompetitionNotFoundException(NetId netId) {
        super(netId.toString());
    }
}


