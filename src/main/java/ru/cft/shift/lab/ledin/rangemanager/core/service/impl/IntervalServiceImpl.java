package ru.cft.shift.lab.ledin.rangemanager.core.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.cft.shift.lab.ledin.rangemanager.api.dto.IntervalDto;
import ru.cft.shift.lab.ledin.rangemanager.core.entity.impl.DigitInterval;
import ru.cft.shift.lab.ledin.rangemanager.core.entity.impl.LetterInterval;
import ru.cft.shift.lab.ledin.rangemanager.core.exception.IntervalNotFoundException;
import ru.cft.shift.lab.ledin.rangemanager.core.exception.InvalidKindException;
import ru.cft.shift.lab.ledin.rangemanager.core.repository.DigitIntervalRepository;
import ru.cft.shift.lab.ledin.rangemanager.core.repository.LetterIntervalRepository;
import ru.cft.shift.lab.ledin.rangemanager.core.service.IntervalService;
import ru.cft.shift.lab.ledin.rangemanager.core.utils.MergeUtil;
import ru.cft.shift.lab.ledin.rangemanager.core.utils.validation.IntervalValidate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing letter and digit intervals.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IntervalServiceImpl implements IntervalService {

    private final LetterIntervalRepository letterIntervalRepository;
    private final DigitIntervalRepository digitIntervalRepository;
    private final IntervalValidate intervalValidate;


    /**
     * Retrieves the minimum interval for the specified kind.
     *
     * @param kind the kind of interval ("digits" for digit intervals, "letters" for letter intervals)
     * @return the minimum interval as {@link IntervalDto}
     * @throws IntervalNotFoundException if no intervals are found for the specified kind
     * @throws InvalidKindException if an invalid kind is provided
     */
    @Override
    public IntervalDto<?> getMinInterval(String kind) {
        if (kind.equals("digits")) {
            log.info("Fetching minimum digit interval...");
            DigitInterval interval = digitIntervalRepository.findMinInterval()
                    .orElseThrow(() -> {
                        log.info("No intervals found for kind: digits");
                        return new IntervalNotFoundException();
                    });
            log.info("Found interval: {}", interval);
            return new IntervalDto<>(interval.getIntervalStart(), interval.getIntervalEnd());
        } else if (kind.equals("letters")) {
            log.info("Fetching minimum letter interval...");
            LetterInterval interval = letterIntervalRepository.findMinInterval()
                    .orElseThrow(() -> {
                        log.info("No intervals found for kind: letters");
                        return new IntervalNotFoundException();
                    });
            log.info("Found interval: {}", interval);
            return new IntervalDto<>(interval.getIntervalStart(), interval.getIntervalEnd());
        } else {
            log.error("Invalid kind: {}", kind);
            throw new InvalidKindException();
        }
    }

    /**
     * Merges the provided intervals for the specified kind.
     *
     * @param kind the kind of interval ("digits" for digit intervals, "letters" for letter intervals)
     * @param intervalDto the list of intervals to merge
     * @throws InvalidKindException if an invalid kind is provided
     */
    @Override
    public void mergeIntervals(String kind, List<IntervalDto<?>> intervalDto) {
        intervalValidate.validateIntervalLength(intervalDto);

        log.info("mergeIntervals called with kind: {} and intervalDto: {}", kind, intervalDto);

        if (kind.equals("digits")) {
            intervalValidate.validateIntervalDigit(intervalDto);
            List<DigitInterval> intervals = intervalDto.stream()
                    .map(interval -> new DigitInterval(
                            (Integer) interval.getStart(),
                            (Integer) interval.getEnd()
                    ))
                    .collect(Collectors.toList());

            for (DigitInterval interval : intervals) {
                intervalValidate.validateIntervalBorder(interval.getStart(), interval.getEnd());
            }

            log.debug("Converted intervalDto to DigitInterval list: {}", intervals);

            @Valid List<DigitInterval> mergedIntervals = MergeUtil.<Integer, DigitInterval>merge(intervals);
            log.debug("Merged DigitInterval list: {}", mergedIntervals);

            digitIntervalRepository.saveAll(mergedIntervals);
            log.info("Saved merged DigitIntervals to repository");
        } else if (kind.equals("letters")) {
            intervalValidate.validateIntervalLetter(intervalDto);
            List<LetterInterval> intervals = intervalDto.stream()
                    .map(interval -> new LetterInterval(
                            (String) interval.getStart(),
                            (String) interval.getEnd()
                    ))
                    .collect(Collectors.toList());

            for (LetterInterval interval : intervals) {
                intervalValidate.validateIntervalBorder(interval.getStart(), interval.getEnd());
            }

            log.debug("Converted intervalDto to LetterInterval list: {}", intervals);

            @Valid List<LetterInterval> mergedIntervals = MergeUtil.<String, LetterInterval>merge(intervals);
            log.debug("Merged LetterInterval list: {}", mergedIntervals);

            letterIntervalRepository.saveAll(mergedIntervals);
            log.info("Saved merged LetterIntervals to repository");
        } else {
            log.error("Invalid kind: {}", kind);
            throw new InvalidKindException();
        }
    }
}
