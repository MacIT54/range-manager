package ru.cft.shift.lab.ledin.rangemanager.core.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.cft.shift.lab.ledin.rangemanager.core.entity.impl.DigitInterval;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class DigitIntervalRepositoryTest {

    @Autowired
    private DigitIntervalRepository digitIntervalRepository;

    @BeforeEach
    public void setUp() {
        digitIntervalRepository.deleteAll();

        DigitInterval interval1 = new DigitInterval(UUID.randomUUID(), 1, 5);
        DigitInterval interval2 = new DigitInterval(UUID.randomUUID(), 6, 10);
        DigitInterval interval3 = new DigitInterval(UUID.randomUUID(), -3, 0);

        digitIntervalRepository.save(interval1);
        digitIntervalRepository.save(interval2);
        digitIntervalRepository.save(interval3);
    }

    @Test
    public void testFindMinInterval() {
        Optional<DigitInterval> minInterval = digitIntervalRepository.findMinInterval();

        assertThat(minInterval).isPresent();
        assertThat(minInterval.get().getIntervalStart()).isEqualTo(-3);
        assertThat(minInterval.get().getIntervalEnd()).isEqualTo(0);
    }
}

