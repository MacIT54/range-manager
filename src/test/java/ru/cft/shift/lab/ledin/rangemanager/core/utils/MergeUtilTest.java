package ru.cft.shift.lab.ledin.rangemanager.core.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.cft.shift.lab.ledin.rangemanager.core.entity.impl.DigitInterval;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MergeUtilTest {

    @Test
    void testMergeEmptyList() {
        List<DigitInterval> intervals = Collections.emptyList();
        List<DigitInterval> result = MergeUtil.merge(intervals);
        assertTrue(result.isEmpty());
    }

    @Test
    void testMergeNonOverlappingIntervals() {
        List<DigitInterval> intervals = Arrays.asList(
                new DigitInterval(1, 2),
                new DigitInterval(3, 4),
                new DigitInterval(5, 6)
        );
        List<DigitInterval> result = MergeUtil.merge(intervals);
        assertEquals(3, result.size());
        assertEquals(new DigitInterval(1, 2), result.get(0));
        assertEquals(new DigitInterval(3, 4), result.get(1));
        assertEquals(new DigitInterval(5, 6), result.get(2));
    }

    @Test
    void testMergeOverlappingIntervals() {
        List<DigitInterval> intervals = Arrays.asList(
                new DigitInterval(1, 3),
                new DigitInterval(2, 5),
                new DigitInterval(6, 8)
        );
        List<DigitInterval> result = MergeUtil.merge(intervals);
        assertEquals(2, result.size());
        assertEquals(new DigitInterval(1, 5), result.get(0));
        assertEquals(new DigitInterval(6, 8), result.get(1));
    }

    @Test
    void testMergeFullyOverlappingIntervals() {
        List<DigitInterval> intervals = Arrays.asList(
                new DigitInterval(1, 10),
                new DigitInterval(2, 3),
                new DigitInterval(4, 8)
        );
        List<DigitInterval> result = MergeUtil.merge(intervals);
        assertEquals(1, result.size());
        assertEquals(new DigitInterval(1, 10), result.get(0));
    }

    @Test
    void testMergeComplexIntervals() {
        List<DigitInterval> intervals = Arrays.asList(
                new DigitInterval(1, 4),
                new DigitInterval(2, 6),
                new DigitInterval(8, 10),
                new DigitInterval(9, 12)
        );
        List<DigitInterval> result = MergeUtil.merge(intervals);
        assertEquals(2, result.size());
        assertEquals(new DigitInterval(1, 6), result.get(0));
        assertEquals(new DigitInterval(8, 12), result.get(1));
    }
}

