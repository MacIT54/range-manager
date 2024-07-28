package ru.cft.shift.lab.ledin.core.entity.impl;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shift.lab.ledin.core.entity.Interval;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "letter_interval")
public class LetterInterval implements Interval<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "interval_id" ,
            updatable = false,
            nullable = false,
            unique=true)
    private UUID id;
    @Column(name = "interval_start")
    @Pattern(regexp = "[a-zA-Z]")
    private String start;
    @Column(name = "interval_end")
    @Pattern(regexp = "[a-zA-Z]")
    private String end;

    public LetterInterval(String start, String end) {
        this.start = start;
        this.end = end;
    }


    @Override
    public UUID getIntervalId() {
        return id;
    }

    @Override
    public void setIntervalEnd(String intervalEnd) {
        this.end = intervalEnd;
    }

    @Override
    public void setIntervalStart(String intervalStart) {
        this.start = intervalStart;
    }


    @Override
    public String getIntervalStart() {
        return start;
    }

    @Override
    public String getIntervalEnd() {
        return end;
    }

    @Override
    public int compareTo(Interval<String> o) {
        return start.compareTo(o.getIntervalStart());
    }
}