package nl.tudelft.sem.template.activity.profiles;

import nl.tudelft.sem.template.activity.authentication.JwtTokenVerifier;
import nl.tudelft.sem.template.activity.domain.services.RestServiceFacade;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("mockRestServiceFacade")
@Configuration
public class MockRestServiceFacadeProfile {

    /**
     * Mocks the RestServiceFacade.
     *
     * @return A mocked RestServiceFacade.
     */
    @Bean
    @Primary  // marks this bean as the first bean to use when trying to inject an AuthenticationManager
    public RestServiceFacade getMockRestServiceFacade() {
        return Mockito.mock(RestServiceFacade.class);
    }
}
