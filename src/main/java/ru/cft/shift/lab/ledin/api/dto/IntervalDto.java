package ru.cft.shift.lab.ledin.api.dto;

import lombok.NoArgsConstructor;
import ru.cft.shift.lab.ledin.core.exception.DefaultException;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;

@NoArgsConstructor
@Schema(name = "IntervalDto", example = "[1, 3]", description = "digits or letters")
public class IntervalDto<T extends Comparable<T>> extends ArrayList<T> {
    public IntervalDto(T start, T end) {
        super();
        if (this.isEmpty()) {
            this.add(start);
            this.add(end);
        }
    }

    public T getStart() {
        if (!this.isEmpty()) {
            return this.get(0);
        } else {
            throw new DefaultException("Empty interval");
        }
    }

    public T getEnd() {
        if (this.size() > 1) {
            return this.get(1);
        } else {
            throw new DefaultException("Empty interval");
        }
    }
}
