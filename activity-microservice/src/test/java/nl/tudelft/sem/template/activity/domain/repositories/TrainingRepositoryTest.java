package nl.tudelft.sem.template.activity.domain.repositories;

import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class TrainingRepositoryTest {

    private TrainingRepository trainingRepository;

    private Training training;

    @BeforeEach
    public void setup() {
        training = new Training(new NetId("123"), "testname", 123L, 123L);
        trainingRepository = new TrainingRepository() {
            @Override
            public Training findByNetId(NetId netId) {
                return training;
            }

            @Override
            public boolean existsByNetId(NetId netId) {
                return false;
            }

            @Override
            public List<Training> findAll() {
                return null;
            }

            @Override
            public List<Training> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<Training> findAllById(Iterable<NetId> netIds) {
                return null;
            }

            @Override
            public <S extends Training> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Training> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public void deleteInBatch(Iterable<Training> entities) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Training getOne(NetId netId) {
                return null;
            }

            @Override
            public <S extends Training> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Training> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<Training> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Training> S save(S entity) {
                return null;
            }

            @Override
            public Optional<Training> findById(NetId netId) {
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
            public void delete(Training entity) {

            }

            @Override
            public void deleteAll(Iterable<? extends Training> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends Training> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Training> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Training> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Training> boolean exists(Example<S> example) {
                return false;
            }
        };
    }

    @Test
    void findByNetId() {
        Assertions.assertEquals(training, trainingRepository.findByNetId(new NetId("123")));
    }

    @Test
    void existsByNetId() {
        Assertions.assertFalse(trainingRepository.existsByNetId(new NetId("123")));
    }
}