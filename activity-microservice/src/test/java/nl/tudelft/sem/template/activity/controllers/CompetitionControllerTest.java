package nl.tudelft.sem.template.activity.controllers;

import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.domain.services.CompetitionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CompetitionControllerTest {

    private CompetitionController competitionController;

    private AuthManager authManager;

    private CompetitionService competitionService;

    private CompetitionRepository competitionRepository;

    @BeforeEach
    public void setup() {
        authManager = new AuthManager();
        competitionRepository = new CompetitionRepository() {
            @Override
            public Competition findByNetId(NetId netId) {
                return null;
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
        competitionController = new CompetitionController(authManager, competitionService);
    }

    @Test
    void helloWorld() {

    }

    @Test
    void createCompetition() {
    }

    @Test
    void findCompetitions() {
    }
}