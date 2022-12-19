package nl.tudelft.sem.template.activity.domain.exceptions;

import nl.tudelft.sem.template.activity.domain.services.RestService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;

class UnsuccessfulRequestExceptionTest {

    @Mock
    private RestService restService;
    @Test
    void testUnsuccessfulRequestException() {
        restService = mock(RestService.class);
        when(RestService.performRequest())
    }

}