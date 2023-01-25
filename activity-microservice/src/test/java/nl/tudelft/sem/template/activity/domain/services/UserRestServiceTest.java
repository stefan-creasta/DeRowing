package nl.tudelft.sem.template.activity.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.exceptions.UnsuccessfulRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;


public class UserRestServiceTest {

    private transient Environment environment;
    private transient RestServiceFacade restServiceFacade;
    private transient UserRestService userRestService;
    private transient AuthManager authManager;
    private transient Object model;

    @BeforeEach
    void setup() {
        environment = mock(Environment.class);
        restServiceFacade = mock(RestServiceFacade.class);
        authManager = mock(AuthManager.class);
        when(authManager.getNetId()).thenReturn("123");
        model = mock(Object.class);
    }

    @Test
    void constructorNullPortTest() {
        when(environment.getProperty("user.port")).thenReturn(null);
        when(environment.getProperty("user.url")).thenReturn("home");
        assertThrows(UnsuccessfulRequestException.class, () -> new UserRestService(environment));
    }

    @Test
    void constructorNullUrlTest() {
        when(environment.getProperty("user.url")).thenReturn(null);
        when(environment.getProperty("user.port")).thenReturn("100");
        assertThrows(UnsuccessfulRequestException.class, () -> new UserRestService(environment));
    }

    @Test
    void constructorNoExceptionsTest() {
        when(environment.getProperty("user.port")).thenReturn("100");
        when(environment.getProperty("user.url")).thenReturn("home");
        assertDoesNotThrow(() -> new UserRestService(environment));
    }

    @Test
    void constructorValidTest() throws UnsuccessfulRequestException {
        when(environment.getProperty("user.port")).thenReturn("100");
        when(environment.getProperty("user.url")).thenReturn("home");
        userRestService = new UserRestService(environment);
        assertEquals(userRestService.getPortString(), "100");
        assertEquals(userRestService.getUrl(), "home");
    }

    @Test
    void deserializeNullTest() throws UnsuccessfulRequestException {
        when(environment.getProperty("user.port")).thenReturn("100");
        when(environment.getProperty("user.url")).thenReturn("home");
        userRestService = new UserRestService(environment);
        assertNull(userRestService.deserialize(null, String.class));
    }

    @Test
    void deserializeTargetNullTest() throws UnsuccessfulRequestException{
        when(environment.getProperty("user.port")).thenReturn("100");
        when(environment.getProperty("user.url")).thenReturn("home");
        userRestService = new UserRestService(environment);
        assertNull(userRestService.deserialize(new Object(), null));
    }

    @Test
    void deserializeMapperTest() {
        when(environment.getProperty("user.port")).thenReturn("100");
        when(environment.getProperty("user.url")).thenReturn("home");
        try {
            Object genericResponse =
                    RestService.performRequest(model, "home", 100, "home", HttpMethod.POST);
            userRestService = new UserRestService(environment);
            when(userRestService.performUserRequest(any(), any(), String.class))
                    .thenReturn(userRestService.deserialize(any(), String.class));
            String response = new ObjectMapper().convertValue(genericResponse, String.class);
            assertEquals(response, userRestService.deserialize(genericResponse, String.class));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
