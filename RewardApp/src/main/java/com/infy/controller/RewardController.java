package com.infy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.service.RewardService;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {
	
	@Autowired
	private RewardService service;
	
	@GetMapping("/monthly/{customerId}")
    public Map<String, Integer> getMonthlyPoints(@PathVariable Long customerId) {
        return service.calculateMonthlyPoints(customerId);
    }

    @GetMapping("/total/{customerId}")
    public int getTotalPoints(@PathVariable Long customerId) {
        return service.calculateTotalPoints(customerId);
    }

}
