package ru.cft.shift.lab.ledin.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.lab.ledin.api.dto.IntervalDto;
import ru.cft.shift.lab.ledin.core.service.IntervalService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/v1/intervals")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "api.intervals.tag.name", description = "api.intervals.tag.description")
public class IntervalController {

    private final IntervalService intervalService;

    @Operation(summary = "api.intervals.merge.operation.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api.intervals.merge.api-responses.200.description"),
            @ApiResponse(responseCode = "400", description = "api.intervals.merge.api-responses.400.description", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "api.server.error", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))})
    })
    @PostMapping("merge")
    public ResponseEntity<Void> mergeIntervals(
            @Parameter(
                    name = "kind",
                    description = "Letters or digits",
                    example = "digits",
                    required = true)
            @RequestParam(name = "kind") String kind,
            @RequestBody
            @NotEmpty(message = "api.error-message.no-intervals")
            List<@Valid IntervalDto<?>> intervalDto) {
        log.info("Received mergeIntervals request with kind: {} and intervals: {}", kind, intervalDto);
        intervalService.mergeIntervals(kind, intervalDto);
        log.info("Processed mergeIntervals request successfully");
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "api.intervals.get-min.operation.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api.intervals.get-min.api-responses.200.description"),
            @ApiResponse(responseCode = "400", description = "api.intervals.get-min.api-responses.400.description", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "api.intervals.get-min.api-responses.404.description", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "api.server.error", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))})
    })
    @GetMapping("min")
    public ResponseEntity<IntervalDto<?>> getMinInterval(
            @Parameter(
                    name = "kind",
                    description = "Letters or digits",
                    example = "digits",
                    required = true)
            @RequestParam(name = "kind") String kind) {
        log.info("Received getMinInterval request with kind: {}", kind);
        IntervalDto<?> minInterval = intervalService.getMinInterval(kind);
        log.info("Processed getMinInterval request successfully, min interval: {}", minInterval);
        return ResponseEntity.ok(minInterval);
    }
}
