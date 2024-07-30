package ru.cft.shift.lab.ledin.rangemanager.core.entity.impl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shift.lab.ledin.rangemanager.core.entity.Interval;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "digit_interval")
public class DigitInterval implements Interval<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "interval_id",
            updatable = false,
            nullable = false,
            unique=true)
    private UUID id;
    @Column(name = "interval_start", nullable = false)
    private Integer start;
    @Column(name = "interval_end", nullable = false)
    private Integer end;

    public DigitInterval(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }


    @Override
    public int compareTo(Interval<Integer> o) {
        return start.compareTo(o.getIntervalStart());
    }

    public Integer getIntervalStart() {
        return start;
    }

    @Override
    public Integer getIntervalEnd() {
        return end;
    }

    @Override
    public UUID getIntervalId() {
        return id;
    }

    @Override
    public void setIntervalEnd(Integer intervalEnd) {
        this.end = intervalEnd;
    }
    @Override
    public void setIntervalStart(Integer intervalStart) {
        this.start = intervalStart;
    }


}
