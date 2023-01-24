package nl.tudelft.sem.template.activity.domain.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.template.activity.authentication.AuthInterface;
import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.exceptions.UnsuccessfulRequestException;
import nl.tudelft.sem.template.activity.models.BoatDeleteModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoatRestServiceTest {
    private transient Environment environment;
    private transient BoatDeleteModel boatDeleteModel;
    private transient BoatRestService boatRestService;
    private transient RestServiceFacade restServiceFacade;
    private transient RestService restService;
    private transient AuthManager authManager;

    private transient Object model;

    @BeforeEach
    void setup() {
        environment = Mockito.mock(Environment.class);
        boatDeleteModel = Mockito.mock(BoatDeleteModel.class);
        restServiceFacade = Mockito.mock(RestServiceFacade.class);
        authManager = mock(AuthManager.class);
        when(authManager.getNetId()).thenReturn("123");

        model = Mockito.mock(Object.class);
    }

    @Test
    void constructorNullPortTest() {
        when(environment.getProperty("boat.port")).thenReturn(null);
        try {
            boatRestService = new BoatRestService(environment);
            assertNull(boatRestService.getPortString());
        } catch(UnsuccessfulRequestException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void constructorNullUrlTest() {
        when(environment.getProperty("boat.url")).thenReturn(null);
        try {
            boatRestService = new BoatRestService(environment);
            assertNull(boatRestService.getUrl());
        } catch(UnsuccessfulRequestException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getPortStringTest() {
        try {
            when(environment.getProperty("boat.port")).thenReturn("100");
            boatRestService = new BoatRestService(environment);
            assertEquals(environment.getProperty("boat.port"), boatRestService.getPortString());
        } catch(UnsuccessfulRequestException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getPortString_MutantTest() {
        try {
            when(environment.getProperty("boat.port")).thenReturn("100");
            boatRestService = new BoatRestService(environment);
            assertNotEquals("", boatRestService.getPortString());
            assertFalse(boatRestService.getPortString().isEmpty());
            assertEquals(environment.getProperty("boat.port"), boatRestService.getPortString());
        } catch(UnsuccessfulRequestException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getUrl_MutantTest() {
        try {
            when(environment.getProperty("boat.url")).thenReturn("home");
            boatRestService = new BoatRestService(environment);
            assertNotEquals("", boatRestService.getUrl());
            assertFalse(boatRestService.getPortString().isEmpty());
            assertEquals(environment.getProperty("boat.url"), boatRestService.getUrl());
        } catch(UnsuccessfulRequestException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getUrlTest() {
        try {
            when(environment.getProperty("boat.url")).thenReturn("home");
            boatRestService = new BoatRestService(environment);
            assertEquals(environment.getProperty("boat.url"), boatRestService.getUrl());
        } catch(UnsuccessfulRequestException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void constructorTest() {
        when(environment.getProperty("boat.port")).thenReturn("100");
        when(environment.getProperty("boat.url")).thenReturn("home");
        try {
            boatRestService = new BoatRestService(environment);
            assertNotNull(boatRestService.getPortString());
            assertNotNull(boatRestService.getUrl());
        } catch (UnsuccessfulRequestException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void deserializeMapperTest() {
        try {
            Object genericResponse = RestService.performRequest(model, "home", 100, "home", HttpMethod.POST);
            boatRestService = new BoatRestService(environment);
            when(boatRestService.performBoatRequest(any(), any(), String.class)).thenReturn(boatRestService.deserialize(any(), String.class));
            String response = new ObjectMapper().convertValue(genericResponse, String.class);
            assertEquals(response, boatRestService.deserialize(genericResponse, String.class));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void deserializeTargetNullTest() {
        try {
            boatRestService = new BoatRestService(environment);
            assertNull(boatRestService.deserialize(any(), null));
        } catch (UnsuccessfulRequestException e) {
            System.out.println(e.getMessage());
        }
    }
}
