package nl.tudelft.sem.template.activity.domain.services;

import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.events.BoatChangeEvent;
import nl.tudelft.sem.template.activity.domain.exceptions.UnsuccessfulRequestException;
import nl.tudelft.sem.template.activity.models.CreateBoatModel;
import nl.tudelft.sem.template.activity.models.CreateBoatResponseModel;
import nl.tudelft.sem.template.activity.models.FindSuitableCompetitionModel;
import nl.tudelft.sem.template.activity.models.FindSuitableCompetitionResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoatRestService extends RestService {
	// Here we can create methods, that use the parent class' performRequest()
	// to get information from other microservices
	private final transient Environment environment;

	@Autowired
	public BoatRestService(Environment environment) {
		this.environment = environment;
	}


	/**
	 * A method to inform the boat service of an extra user.
	 *
	 * @param model the model to insert a user into a boat
	 * @return boolean whether the request was successful
	 */
	public boolean informBoatOfJoining(BoatChangeEvent model) {
		String url = environment.getProperty("boat.url");
		int port = Integer.parseInt(environment.getProperty("boat.port"));
		try {
			performRequest(model, url, port, "/boat/insert", HttpMethod.POST);
			return true;
		} catch (UnsuccessfulRequestException e) {
			System.out.println("Boat microservice seems unavailable");
			return false;
		}
	}

	/**
	 * Tells the boat service to create a new boat.
	 *
	 * @param type      the type of boat to create
	 * @param numPeople the number of people the boat can hold
	 * @return the boat id of the newly creted boat.
	 */
	public long getBoatId(Type type, int numPeople) {
		String url = environment.getProperty("boat.url");
		int port = Integer.parseInt(environment.getProperty("boat.port"));
		CreateBoatModel model = new CreateBoatModel();
		model.setType(type);
		model.setNumPeople(numPeople);
		try {
			CreateBoatResponseModel response =
				(CreateBoatResponseModel)
					performRequest(model, url, port, "/boat/create", HttpMethod.POST);
			if (response == null) {
				return -1;
			}
			return response.getBoatId();
		} catch (UnsuccessfulRequestException e) {
			System.out.println("Boat microservice seems unavailable");
			return -1;
		}
	}


	public List<Competition> checkIfPositionAvailable(List<Competition> competitions, Position position) {
		String url = environment.getProperty("boat.url");
		int port = Integer.parseInt(environment.getProperty("boat.port"));
		FindSuitableCompetitionModel model = new FindSuitableCompetitionModel(competitions, position);
		try {
			FindSuitableCompetitionResponseModel response =
				(FindSuitableCompetitionResponseModel)
					performRequest(model, url, port, "/boat/check", HttpMethod.POST);
			return response.getCompetitions();
		} catch (UnsuccessfulRequestException e) {
			System.out.println("There is no such competition that you are suitable for");
			return new ArrayList<>();
		}
	}
}
