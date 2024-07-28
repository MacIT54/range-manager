package ru.cft.shift.lab.ledin.core.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.cft.shift.lab.ledin.core.entity.impl.LetterInterval;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class LetterIntervalRepositoryTest {

    @Autowired
    private LetterIntervalRepository letterIntervalRepository;

    @Test
    void testFindMinInterval() {
        letterIntervalRepository.deleteAll();

        LetterInterval interval1 = new LetterInterval(UUID.randomUUID(), "a", "c");
        LetterInterval interval2 = new LetterInterval(UUID.randomUUID(), "b", "d");
        LetterInterval interval3 = new LetterInterval(UUID.randomUUID(), "e", "g");
        letterIntervalRepository.save(interval1);
        letterIntervalRepository.save(interval2);
        letterIntervalRepository.save(interval3);

        Optional<LetterInterval> minInterval = letterIntervalRepository.findMinInterval();

        assertTrue(minInterval.isPresent());
        assertEquals(interval1, minInterval.get());
    }

    @Test
    void testFindMinIntervalEmpty() {
        letterIntervalRepository.deleteAll();

        Optional<LetterInterval> minInterval = letterIntervalRepository.findMinInterval();

        assertFalse(minInterval.isPresent());
    }
}

