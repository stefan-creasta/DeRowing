package nl.tudelft.sem.template.user.domain.services;

import nl.tudelft.sem.template.user.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final transient UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
