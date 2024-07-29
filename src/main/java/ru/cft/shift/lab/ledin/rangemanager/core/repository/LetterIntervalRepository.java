package ru.cft.shift.lab.ledin.rangemanager.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.cft.shift.lab.ledin.rangemanager.core.entity.impl.LetterInterval;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LetterIntervalRepository extends JpaRepository<LetterInterval, UUID> {
    @Query(value = "SELECT * FROM letter_interval ORDER BY interval_start, interval_end ASC FETCH FIRST 1 ROW ONLY;", nativeQuery = true)
    Optional<LetterInterval> findMinInterval();
}