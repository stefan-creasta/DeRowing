package nl.tudelft.sem.template.activity.domain;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class BoatRestService extends RestService {
    // Here we can create methods, that use the parent class' performRequest() to get information from other microservices
}
