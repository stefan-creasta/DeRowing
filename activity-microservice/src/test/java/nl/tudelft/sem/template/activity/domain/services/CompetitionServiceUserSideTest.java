package nl.tudelft.sem.template.activity.domain.services;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.eq;

import nl.tudelft.sem.template.activity.domain.Certificate;
import nl.tudelft.sem.template.activity.domain.Gender;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.provider.implement.CurrentTimeProvider;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.models.FindSuitableActivityModel;
import nl.tudelft.sem.template.activity.models.FindSuitableActivityResponseModel;
import nl.tudelft.sem.template.activity.models.PositionEntryModel;
import nl.tudelft.sem.template.activity.models.UserDataRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class CompetitionServiceUserSideTest {

    CompetitionServiceUserSide competitionService;
    EventPublisher eventPublisher;
    RestServiceFacade restServiceFacade;
    CurrentTimeProvider currentTimeProvider;
    CompetitionRepository competitionRepository;
    UserDataRequestModel model;
    Competition competition;
    Competition competition1;
    UserDataRequestModel model1;
    Competition competition2;
    Competition competition3;
    Competition competition4;
    Competition competition5;
    Competition competition6;
    Competition competition7;
    Competition competition8;
    Competition competition9;
    Competition competition10;
    Competition competition11;

    @BeforeEach
    void setup() {
        eventPublisher = mock(EventPublisher.class);
        restServiceFacade = mock(RestServiceFacade.class);
        currentTimeProvider = new CurrentTimeProvider();
        competitionRepository = mock(CompetitionRepository.class);
        competitionService =
            new CompetitionServiceUserSide(eventPublisher,
                competitionRepository,
                restServiceFacade,
                currentTimeProvider);
        model = new UserDataRequestModel(Gender.MALE, "Delft", true, Certificate.C4);
        competition = new Competition(new NetId("Viet Luong"), "Rowing", 1L, 1000L,
            false, GenderConstraint.NO_CONSTRAINT, true, "Delft", Type.C4);
        competition1 = new Competition(new NetId("Viet Luong"), "Rowing", 1L, 1000L,
            true, GenderConstraint.NO_CONSTRAINT, true, "Delft", Type.C4);
        model1 = new UserDataRequestModel(Gender.MALE, "Delft", false, Certificate.C4);
        competition2 = new Competition(new NetId("Viet Luong"),
            "Rowing", 2L, Long.MAX_VALUE, true,
            GenderConstraint.NO_CONSTRAINT, false, "Delft", Type.C4);
        competition3 = new Competition(new NetId("Hoang Minh"),
            "Rowing", 3L, Long.MAX_VALUE, true,
            GenderConstraint.ONLY_FEMALE, false, "Delft", Type.C4);
        competition4 = new Competition(new NetId("Manh Tan"), "Rowing",
            4L, Long.MAX_VALUE, true, GenderConstraint.NO_CONSTRAINT,
            true, "Delft", Type.C4);
        competition5 = new Competition(new NetId("Minh Tan"),
            "Rowing", 5L, Long.MAX_VALUE, false, GenderConstraint.ONLY_MALE,
            false, "Delft", Type.C4);
        competition6 = new Competition(new NetId("Minh Tan"),
            "Rowing", 6L, Long.MAX_VALUE,
            true, GenderConstraint.ONLY_MALE,
            false, "Rotterdam", Type.C4);
        competition7 = new Competition(new NetId("Minh Tan"),
            "Rowing", 7L, Long.MAX_VALUE, true, GenderConstraint.ONLY_MALE,
            true, "Amsterdam", Type.C4);
        competition8 = new Competition(new NetId("Minh Tan"),
            "Rowing", 8L, currentTimeProvider.getCurrentTime().toEpochMilli()
            + 86400000, true, GenderConstraint.ONLY_MALE,
            true, "Delft", Type.C4);
        competition9 = new Competition(new NetId("Minh Tan"),
            "Rowing", 9L, 0, true, GenderConstraint.ONLY_MALE,
            true, "Delft", Type.C4);
        competition10 = new Competition(new NetId("Minh Tan"),
            "Rowing", 10L, currentTimeProvider.getCurrentTime().toEpochMilli() + 864000001,
            true, GenderConstraint.ONLY_MALE,
            true, "Delft", Type.C4);
        competition11 = new Competition(new NetId("Minh Tan"),
            "Rowing", 11L, currentTimeProvider.getCurrentTime().toEpochMilli() + 86399999,
            true, GenderConstraint.ONLY_MALE,
            true, "Delft", Type.C4);

    }

    @Test
    void getSuitableCompetitionTestThrowException() throws Exception {
        when(restServiceFacade.performUserModel(null, "/getdetails", UserDataRequestModel.class)).thenReturn(null);
        PositionEntryModel position = new PositionEntryModel(Position.STARBOARD);
        assertThrows(Exception.class, () -> competitionService.getSuitableCompetition(position),
            "We could not get your user information from the user service");
    }

    @Test
    void getSuitableCompetitionTest() throws Exception {
        when(restServiceFacade.performUserModel(null, "/getdetails", UserDataRequestModel.class)).thenReturn(model);
        FindSuitableActivityResponseModel findSuitableActivityResponse
            = new FindSuitableActivityResponseModel(List.of(1L, 2L, 3L));
        when(restServiceFacade.performBoatModel(any(FindSuitableActivityModel.class), eq("/boat/check"),
            eq(FindSuitableActivityResponseModel.class)))
            .thenReturn(findSuitableActivityResponse);
        PositionEntryModel position = new PositionEntryModel(Position.STARBOARD);
        competitionService.getSuitableCompetition(position);
        verify(competitionRepository).findAllByBoatIdIn(findSuitableActivityResponse.getBoatId());
    }

    @Test
    void suitableBoatIdsMutateCheckGender() {
        when(competitionRepository.findAll()).thenReturn(List.of(competition2, competition3));
        List<Long> boatIds = competitionService.suitableBoatIds(competitionRepository, model);
        assertEquals(List.of(2L), boatIds);
    }

    @Test
    void suitableBoatIdsMutateCheckAmateur() {
        when(competitionRepository.findAll()).thenReturn(List.of(competition4, competition5));
        List<Long> boatIds = competitionService.suitableBoatIds(competitionRepository, model);
        assertEquals(List.of(4L), boatIds);
    }

    @Test
    void suitableBoatIdsMutateCheckOrganization() {
        when(competitionRepository.findAll()).thenReturn(List.of(competition6, competition7));
        List<Long> boatIds = competitionService.suitableBoatIds(competitionRepository, model);
        assertEquals(List.of(6L), boatIds);
    }

    @Test
    void suitableBoatIdsMutateStartTimeCondition() {
        when(competitionRepository.findAll()).thenReturn(List.of(competition8, competition9, competition10, competition11));
        List<Long> boatIds = competitionService.suitableBoatIds(competitionRepository, model);
        assertEquals(List.of(10L), boatIds);
    }

    @Test
    void checkAmateur() {
        assertFalse(competitionService.checkAmateur(competition, model));
        assertTrue(competitionService.checkAmateur(competition1, model));
        assertTrue(competitionService.checkAmateur(competition, model1));
        assertTrue(competitionService.checkAmateur(competition1, model1));
    }

    @Test
    void checkOriganization() {
        Competition competition = new Competition(new NetId("Viet Luong"),
            "Rowing", 1L, 1000L,
            false, GenderConstraint.NO_CONSTRAINT,
            true, "Delft", Type.C4);
        Competition firstCompetition = new Competition(new NetId("Viet Luong"),
            "Rowing", 1L, 1000L,
            false, GenderConstraint.NO_CONSTRAINT,
            true, "qwerqwer", Type.C4);

        final Competition secondCompetition = new Competition(new NetId("Viet Luong"), "Rowing", 1L, 1000L,
            false, GenderConstraint.NO_CONSTRAINT, false, "qwerqwer", Type.C4);
        UserDataRequestModel model = new UserDataRequestModel(Gender.MALE, "Delft", true, Certificate.C4);
        UserDataRequestModel model1 = new UserDataRequestModel(Gender.MALE, "qwerqwer", true, Certificate.C4);
        assertTrue(competitionService.checkOriganization(competition, model));
        assertFalse(competitionService.checkOriganization(firstCompetition, model));
        assertTrue(competitionService.checkOriganization(firstCompetition, model1));
        assertTrue(competitionService.checkOriganization(secondCompetition, model1));
    }
}