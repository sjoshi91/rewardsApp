package com.infy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.infy.service.RewardService;


@WebMvcTest(RewardController.class)
@ActiveProfiles("test")
public class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMonthlyPoints() throws Exception {
        Map<String, Integer> monthlyPoints = new HashMap<>();
        monthlyPoints.put("JANUARY", 90);
        when(rewardService.calculateMonthlyPoints(1L)).thenReturn(monthlyPoints);

        mockMvc.perform(get("/api/rewards/monthly/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"JANUARY\": 90}"));
    }

    @Test
    public void testGetTotalPoints() throws Exception {
        when(rewardService.calculateTotalPoints(1L)).thenReturn(115);

        mockMvc.perform(get("/api/rewards/total/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("115"));
    }
}