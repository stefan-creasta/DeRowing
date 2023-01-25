package nl.tudelft.sem.template.activity.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.provider.implement.CurrentTimeProvider;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.CreateBoatResponseModel;
import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import nl.tudelft.sem.template.activity.models.CompetitionEditModel;
import nl.tudelft.sem.template.activity.models.BoatDeleteModel;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.time.Instant;

class CompetitionServiceServerSideTest {

    private CompetitionServiceServerSide competitionServiceServerSide;

    private CompetitionServiceUserSide competitionServiceUserSide;

    private CompetitionCreateModel competitionCreateModel;

    @Mock
    private CompetitionRepository competitionRepository;

    @Mock
    private BoatRestService boatRestService;

    private Competition competition;

    private NetId id;

    @Mock
    private EventPublisher eventPublisher;

    @Mock
    private UserRestService userRestService;

    @Mock
    private RestServiceFacade restServiceFacade;

    @Mock
    private CurrentTimeProvider currentTimeProvider;

    @Mock
    private AuthManager authManager;

    private JoinRequestModel joinRequestModel;

    private AcceptRequestModel acceptRequestModel;

    @BeforeEach
    public void setup() {
        competitionCreateModel = new CompetitionCreateModel("test", GenderConstraint.NO_CONSTRAINT,
                true, false, "TUDELFT", 123L, Type.C4);
        eventPublisher = mock(EventPublisher.class);
        competitionRepository = mock(CompetitionRepository.class);
        this.restServiceFacade = mock(RestServiceFacade.class);
        currentTimeProvider = mock(CurrentTimeProvider.class);
        authManager = mock(AuthManager.class);
        joinRequestModel = new JoinRequestModel();
        joinRequestModel.setActivityId(123L);
        joinRequestModel.setPosition(Position.COACH);
        acceptRequestModel = new AcceptRequestModel();
        competitionServiceServerSide = new CompetitionServiceServerSide(competitionRepository, restServiceFacade);
        competitionServiceUserSide = new CompetitionServiceUserSide(eventPublisher, competitionRepository,
                restServiceFacade, currentTimeProvider);
        id = new NetId("123");
        competition = new Competition(id, competitionCreateModel.getCompetitionName(), 123L, 123L,
                true, GenderConstraint.NO_CONSTRAINT, false, "TUDELFT", Type.C4);

    }

    @Test
    void parseRequest() {
        assertEquals(competition, competitionServiceServerSide
                .parseRequest(competitionCreateModel, id, 123L));
    }

    @Test
    void createCompetition() throws Exception {
        when(restServiceFacade.performBoatModel(any(), any(), any())).thenReturn(new CreateBoatResponseModel(123L));
        when(competitionRepository.save(competition)).thenReturn(competition);
        assertEquals("Successfully created competition",
                competitionServiceServerSide.createCompetition(competitionCreateModel, new NetId("123")));
    }

    @Test
    void joinCompetition() throws Exception {
        when(competitionRepository.findById(123L)).thenReturn(competition);
        when(currentTimeProvider.getCurrentTime()).thenReturn(Instant.ofEpochSecond(123L));
        assertEquals("Sorry you can't join this competition "
                + "since it will start in one day.", competitionServiceUserSide.joinCompetition(joinRequestModel));
    }

    @Test
    void testFindCompetition() throws Exception {
        when(competitionRepository.findById(123L)).thenReturn(competition);
        assertEquals(competition, competitionServiceUserSide.findCompetition(123L));
    }

    @Test
    void deleteCompetition() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        BoatDeleteModel boatDeleteModel = new BoatDeleteModel(123L);
        when(competitionServiceUserSide.findCompetition(123L)).thenReturn(competition);
        assertEquals("Successfully deleted competition",
                competitionServiceServerSide.deleteCompetition(123L, authManager.getNetId()));
    }

    @Test
    void update() {
        CompetitionEditModel competitionEditModel = new CompetitionEditModel();
        competitionEditModel.setCompetitionName("newName");
        competitionEditModel.setAllowAmateurs(true);
        competitionEditModel.setGenderConstraint(GenderConstraint.NO_CONSTRAINT);
        competitionEditModel.setSingleOrganization(false);
        competitionEditModel.setOrganization("TUDELFT");
        Competition temp = new Competition(id, "newName", 123L, 0, true,
                GenderConstraint.NO_CONSTRAINT, false, "TUDELFT", Type.C4);
        competition = competitionServiceServerSide.update(competition, competitionEditModel);
        assertEquals(temp, competition);
    }

    @Test
    void editCompetition() throws Exception {
        when(competitionRepository.findById(123L)).thenReturn(competition);
        Competition temp = new Competition(id, "newName", 123L, 0, true,
                GenderConstraint.NO_CONSTRAINT, false, "TUDELFT", Type.C4);
        CompetitionEditModel competitionEditModel = new CompetitionEditModel();
        competitionEditModel.setCompetitionName("newName");
        competitionEditModel.setAllowAmateurs(true);
        competitionEditModel.setGenderConstraint(GenderConstraint.NO_CONSTRAINT);
        competitionEditModel.setSingleOrganization(false);
        competitionEditModel.setOrganization("TUDELFT");
        Assertions.assertThrows(Exception.class, () -> {
            competitionServiceServerSide.editCompetition(competitionEditModel, authManager.getNetId());
        });
    }

    @Test
    void editCompetitionNotOwnerTest() throws Exception {
        // Create input
        CompetitionEditModel model = new CompetitionEditModel();
        model.setId(1);
        // We return a competition with a different netid as owner
        Competition competition = new Competition();
        competition.setOwner(new NetId("notmaarten"));
        when(competitionRepository.findById(1)).thenReturn(competition);

        String response = competitionServiceServerSide.editCompetition(model, "maarten");
        assertEquals("You are not the owner of this competition", response);
        verify(competitionRepository, times(1)).findById(1);
    }

    @Test
    void deleteCompetitionNotOwnerTest() throws Exception {
        // Create input
        CompetitionEditModel model = new CompetitionEditModel();
        model.setId(1);
        // We return a training with a different netid as owner
        Competition competition = new Competition();
        competition.setOwner(new NetId("notmaarten"));
        when(competitionRepository.findById(1)).thenReturn(competition);


        String response = competitionServiceServerSide.deleteCompetition(1, "maarten");

        assertEquals("You are not the owner of this competition", response);
        verify(competitionRepository, times(1)).findById(1);

    }
}
