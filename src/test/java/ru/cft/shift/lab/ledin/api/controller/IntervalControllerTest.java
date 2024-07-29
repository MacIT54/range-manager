package ru.cft.shift.lab.ledin.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.cft.shift.lab.ledin.api.dto.IntervalDto;
import ru.cft.shift.lab.ledin.core.service.IntervalService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = IntervalController.class)
public class IntervalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IntervalService intervalService;

    @MockBean
    private PropertyResolverUtils propertyResolverUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testMergeIntervalsDigit() throws Exception {
        List<IntervalDto<?>> intervals = List.of(new IntervalDto<>(1, 5), new IntervalDto<>(6, 10));
        doNothing().when(intervalService).mergeIntervals(eq("digits"), any());

        mockMvc.perform(post("/api/v1/intervals/merge")
                        .param("kind", "digits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(intervals)))
                .andExpect(status().isOk());

        verify(intervalService, times(1)).mergeIntervals(eq("digits"), any());
    }

    @Test
    public void testGetMinIntervalDigit() throws Exception {
        when(intervalService.getMinInterval("digits")).thenReturn((IntervalDto) new IntervalDto<>(1, 5));

        mockMvc.perform(get("/api/v1/intervals/min")
                        .param("kind", "digits")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(1))
                .andExpect(jsonPath("$[1]").value(5));

        verify(intervalService, times(1)).getMinInterval("digits");
    }

    @Test
    public void testMergeIntervalsLetter() throws Exception {
        List<IntervalDto<?>> intervals = List.of(new IntervalDto<>("a", "f"), new IntervalDto<>("d", "j"));
        doNothing().when(intervalService).mergeIntervals(eq("letters"), any());

        mockMvc.perform(post("/api/v1/intervals/merge")
                        .param("kind", "letters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(intervals)))
                .andExpect(status().isOk());

        verify(intervalService, times(1)).mergeIntervals(eq("letters"), any());
    }

    @Test
    public void testGetMinIntervalLetter() throws Exception {
        when(intervalService.getMinInterval("letters")).thenReturn((IntervalDto) new IntervalDto<>("a", "f"));

        mockMvc.perform(get("/api/v1/intervals/min")
                        .param("kind", "letters")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("a"))
                .andExpect(jsonPath("$[1]").value("f"));

        verify(intervalService, times(1)).getMinInterval("letters");
    }
}
