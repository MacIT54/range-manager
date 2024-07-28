package ru.cft.shift.lab.ledin.core.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.cft.shift.lab.ledin.api.dto.IntervalDto;
import ru.cft.shift.lab.ledin.core.entity.impl.DigitInterval;
import ru.cft.shift.lab.ledin.core.entity.impl.LetterInterval;
import ru.cft.shift.lab.ledin.core.exception.IntervalNotFoundException;
import ru.cft.shift.lab.ledin.core.exception.InvalidKindException;
import ru.cft.shift.lab.ledin.core.repository.DigitIntervalRepository;
import ru.cft.shift.lab.ledin.core.repository.LetterIntervalRepository;
import ru.cft.shift.lab.ledin.core.service.impl.IntervalServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class IntervalServiceTest {

    @Mock
    private LetterIntervalRepository letterIntervalRepository;

    @Mock
    private DigitIntervalRepository digitIntervalRepository;

    @InjectMocks
    private IntervalServiceImpl intervalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMinIntervalDigits() {
        DigitInterval digitInterval = new DigitInterval(1, 5);
        when(digitIntervalRepository.findMinInterval()).thenReturn(Optional.of(digitInterval));

        IntervalDto<?> result = intervalService.getMinInterval("digits");

        assertNotNull(result);
        assertEquals(1, result.getStart());
        assertEquals(5, result.getEnd());
    }

    @Test
    void testGetMinIntervalDigitsNotFound() {
        when(digitIntervalRepository.findMinInterval()).thenReturn(Optional.empty());

        assertThrows(IntervalNotFoundException.class, () -> {
            intervalService.getMinInterval("digits");
        });
    }

    @Test
    void testGetMinIntervalLetters() {
        LetterInterval letterInterval = new LetterInterval("a", "d");
        when(letterIntervalRepository.findMinInterval()).thenReturn(Optional.of(letterInterval));

        IntervalDto<?> result = intervalService.getMinInterval("letters");

        assertNotNull(result);
        assertEquals("a", result.getStart());
        assertEquals("d", result.getEnd());
    }

    @Test
    void testGetMinIntervalLettersNotFound() {
        when(letterIntervalRepository.findMinInterval()).thenReturn(Optional.empty());

        assertThrows(IntervalNotFoundException.class, () -> {
            intervalService.getMinInterval("letters");
        });
    }

    @Test
    void testGetMinIntervalInvalidKind() {
        assertThrows(InvalidKindException.class, () -> {
            intervalService.getMinInterval("invalid");
        });
    }


    @Test
    void testMergeIntervalsDigits() {
        List<IntervalDto<?>> intervalDtos = Arrays.asList(
                new IntervalDto<>(1, 5),
                new IntervalDto<>(6, 10)
        );

        List<DigitInterval> digitIntervals = Arrays.asList(
                new DigitInterval(1, 5),
                new DigitInterval(6, 10)
        );

        intervalService.mergeIntervals("digits", intervalDtos);

        verify(digitIntervalRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testMergeIntervalsLetters() {
        List<IntervalDto<?>> intervalDtos = Arrays.asList(
                new IntervalDto<>("a", "d"),
                new IntervalDto<>("e", "h")
        );

        List<LetterInterval> letterIntervals = Arrays.asList(
                new LetterInterval("a", "d"),
                new LetterInterval("e", "h")
        );

        intervalService.mergeIntervals("letters", intervalDtos);

        verify(letterIntervalRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testMergeIntervalsInvalidKind() {
        List<IntervalDto<?>> intervalDtos = Arrays.asList(
                new IntervalDto<>(1, 5),
                new IntervalDto<>(6, 10)
        );

        assertThrows(InvalidKindException.class, () -> {
            intervalService.mergeIntervals("invalid", intervalDtos);
        });
    }
}
