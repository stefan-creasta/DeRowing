package nl.tudelft.sem.template.activity.domain.services;

import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CompetitionServiceTest {

    private CompetitionService competitionService;

    private CompetitionCreateModel competitionCreateModel;

    private Competition competition;

    private NetId id;

    @BeforeEach
    public void setup() {
        competitionCreateModel = new CompetitionCreateModel("test", GenderConstraint.ONLY_MALE,
                123L, false, false, 123L);
        id = new NetId("123");
        competition = new Competition(id, competitionCreateModel.getCompetitionName(),
                competitionCreateModel.getBoatId(), competitionCreateModel.getStartTime(),
                competitionCreateModel.isAllowAmateurs(), competitionCreateModel.getGenderConstraint(),
                competitionCreateModel.isSingleOrganization());
        CompetitionRepository competitionRepository = new CompetitionRepository() {
            @Override
            public Competition findByNetId(NetId netId) {
                return competition;
            }

            @Override
            public boolean existsByNetId(NetId netId) {
                return false;
            }

            @Override
            public List<Competition> findAll() {
                return null;
            }

            @Override
            public List<Competition> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<Competition> findAllById(Iterable<NetId> netIds) {
                return null;
            }

            @Override
            public <S extends Competition> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Competition> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public void deleteInBatch(Iterable<Competition> entities) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Competition getOne(NetId netId) {
                return null;
            }

            @Override
            public <S extends Competition> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Competition> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<Competition> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Competition> S save(S entity) {
                return null;
            }

            @Override
            public Optional<Competition> findById(NetId netId) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(NetId netId) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(NetId netId) {

            }

            @Override
            public void delete(Competition entity) {

            }

            @Override
            public void deleteAll(Iterable<? extends Competition> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends Competition> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Competition> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Competition> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Competition> boolean exists(Example<S> example) {
                return false;
            }
        };
        competitionService = new CompetitionService(competitionRepository);
    }

    @Test
    void parseRequest() {
        Assertions.assertEquals(competition, competitionService.parseRequest(competitionCreateModel, id));
    }

    @Test
    void createCompetition() throws Exception {
        Assertions.assertEquals(competition, competitionService.createCompetition(competitionCreateModel, id));
    }

    @Test
    void findCompetitions() throws Exception {
        Assertions.assertEquals(competition, competitionService.findCompetitions(id));
    }
}