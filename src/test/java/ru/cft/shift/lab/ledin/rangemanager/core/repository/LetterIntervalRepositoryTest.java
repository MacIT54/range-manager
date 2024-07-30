package ru.cft.shift.lab.ledin.rangemanager.core.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.cft.shift.lab.ledin.rangemanager.core.entity.impl.LetterInterval;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class LetterIntervalRepositoryTest {

    @Autowired
    private LetterIntervalRepository letterIntervalRepository;

    @BeforeEach
    public void setUp() {
        letterIntervalRepository.deleteAll();

        LetterInterval interval1 = new LetterInterval(UUID.randomUUID(), "a", "b");
        LetterInterval interval2 = new LetterInterval(UUID.randomUUID(), "d", "j");
        LetterInterval interval3 = new LetterInterval(UUID.randomUUID(), "r", "z");

        letterIntervalRepository.save(interval1);
        letterIntervalRepository.save(interval2);
        letterIntervalRepository.save(interval3);
    }

    @Test
    public void testFindMinInterval() {
        Optional<LetterInterval> minInterval = letterIntervalRepository.findMinInterval();

        assertThat(minInterval).isPresent();
        assertThat(minInterval.get().getIntervalStart()).isEqualTo("a");
        assertThat(minInterval.get().getIntervalEnd()).isEqualTo("b");
    }
}

