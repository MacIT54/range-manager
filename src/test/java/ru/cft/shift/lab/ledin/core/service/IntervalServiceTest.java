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
import ru.cft.shift.lab.ledin.core.utils.validation.IntervalValidate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class IntervalServiceTest {

    @Mock
    private LetterIntervalRepository letterIntervalRepository;

    @Mock
    private IntervalValidate intervalValidate;

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
    public void testMergeIntervalsDigits() {
        List<IntervalDto<?>> intervalDtos = List.of(new IntervalDto<>(1, 5), new IntervalDto<>(6, 10));
        List<DigitInterval> mergedIntervals = List.of(new DigitInterval(1, 10));

        doNothing().when(intervalValidate).validateIntervalLength(intervalDtos);
        doNothing().when(intervalValidate).validateIntervalDigit(intervalDtos);
        doNothing().when(intervalValidate).validateIntervalBorder(anyInt(), anyInt());
        when(digitIntervalRepository.saveAll(any())).thenReturn(mergedIntervals);

        intervalService.mergeIntervals("digits", intervalDtos);

        verify(intervalValidate, times(1)).validateIntervalLength(intervalDtos);
        verify(intervalValidate, times(1)).validateIntervalDigit(intervalDtos);
        verify(intervalValidate, times(2)).validateIntervalBorder(anyInt(), anyInt());
        verify(digitIntervalRepository, times(1)).saveAll(any());
    }

    @Test
    public void testMergeIntervalsLetters() {
        List<IntervalDto<?>> intervalDto = List.of(new IntervalDto<>("a", "e"), new IntervalDto<>("f", "j"));
        List<LetterInterval> mergedIntervals = List.of(new LetterInterval("a", "j"));

        doNothing().when(intervalValidate).validateIntervalLength(intervalDto);
        doNothing().when(intervalValidate).validateIntervalLetter(intervalDto);
        doNothing().when(intervalValidate).validateIntervalBorder(anyString(), anyString());
        when(letterIntervalRepository.saveAll(any())).thenReturn(mergedIntervals);

        intervalService.mergeIntervals("letters", intervalDto);

        verify(intervalValidate, times(1)).validateIntervalLength(intervalDto);
        verify(intervalValidate, times(1)).validateIntervalLetter(intervalDto);
        verify(intervalValidate, times(2)).validateIntervalBorder(anyString(), anyString());
        verify(letterIntervalRepository, times(1)).saveAll(any());
    }

    @Test
    public void testMergeIntervalsInvalidKind() {
        List<IntervalDto<?>> intervalDto = Collections.emptyList();
        doNothing().when(intervalValidate).validateIntervalLength(intervalDto);

        assertThrows(InvalidKindException.class, () -> intervalService.mergeIntervals("invalid", intervalDto));
        verify(intervalValidate, times(1)).validateIntervalLength(intervalDto);
        verify(digitIntervalRepository, never()).saveAll(any());
        verify(letterIntervalRepository, never()).saveAll(any());
    }
}
