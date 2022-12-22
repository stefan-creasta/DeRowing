package nl.tudelft.sem.template.user.domain.services;

import nl.tudelft.sem.template.user.domain.repositories.MessageRepository;
import nl.tudelft.sem.template.user.domain.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class UserServiceTest {
    private transient UserRepository userRepository;
    private transient MessageRepository messageRepository;
    private transient  UserService userService;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        messageRepository = mock(MessageRepository.class);
        userService = new UserService(userRepository, messageRepository);
    }
}
