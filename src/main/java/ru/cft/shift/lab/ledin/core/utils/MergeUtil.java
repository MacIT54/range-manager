package ru.cft.shift.lab.ledin.core.utils;

import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.lab.ledin.core.entity.Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class MergeUtil {
    public static <U extends Comparable<U>, T extends Interval<U>> List<T> merge(List<T> intervals) {
        log.info("merge called with intervals: {}", intervals);

        if (intervals.isEmpty()) {
            log.info("Received empty interval list, returning empty list.");
            return intervals;
        }

        Collections.sort(intervals, Comparator.comparing(Interval::getIntervalStart));
        log.debug("Sorted intervals: {}", intervals);

        List<T> mergedIntervals = new ArrayList<>();
        T current = intervals.get(0);
        mergedIntervals.add(current);
        log.debug("Initialized mergedIntervals with first interval: {}", current);

        for (T interval : intervals.subList(1, intervals.size())) {
            log.debug("Processing interval: {}", interval);
            if (current.getIntervalEnd().compareTo(interval.getIntervalStart()) >= 0) {
                U newEnd = current.getIntervalEnd().compareTo(interval.getIntervalEnd()) >= 0 ?
                        current.getIntervalEnd() : interval.getIntervalEnd();
                current.setIntervalEnd(newEnd);
                log.debug("Merged with current interval, new current: {}", current);
            } else {
                current = interval;
                mergedIntervals.add(current);
                log.debug("Added new interval to mergedIntervals, new current: {}", current);
            }
        }

        log.info("Merged intervals result: {}", mergedIntervals);
        return mergedIntervals;
    }
}
