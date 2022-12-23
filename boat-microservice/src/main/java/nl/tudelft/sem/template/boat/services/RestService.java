package nl.tudelft.sem.template.boat.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

public abstract class RestService {
    /**
     * A method to perform a request to another microservice.
     *
     *
     * @param requestModel The request body to send to the microservice.
     * @param uri         The URI of the microservice.
     * @param port      The port of the microservice.
     * @param path     The path of the microservice.
     * @param method   The method of the microservice. (GET, POST, PUT, DELETE)
     * @return The response of the microservice.
     */
    public static Object performRequest(Object requestModel, String uri, int port, String path, HttpMethod method)
            throws Exception {
        String target = uri + ":" + port + "/" + path;

        RestTemplate request = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = (String) authentication.getCredentials();

        headers.add("Authorization", "Bearer " + jwtToken);
        HttpEntity<Object> entity = new HttpEntity<>(requestModel, headers);
        ResponseEntity<Object> response = request.exchange(target, method, entity, Object.class);
        HttpStatus responseCode = response.getStatusCode();

        if (!responseCode.is2xxSuccessful()) {
            throw new Exception("Request unsuccessful!");
        }
        return response.getBody();
    }
}