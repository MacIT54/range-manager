package ru.cft.shift.lab.ledin.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.cft.shift.lab.ledin.core.entity.impl.DigitInterval;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DigitIntervalRepository extends JpaRepository<DigitInterval, UUID> {
    @Query(value = "SELECT * FROM digit_interval ORDER BY interval_start, interval_end ASC FETCH FIRST 1 ROW ONLY;", nativeQuery = true)
    Optional<DigitInterval> findMinInterval();
}