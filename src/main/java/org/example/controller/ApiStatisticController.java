package org.example.controller;

import org.example.service.StatisticServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/statistics")
public class ApiStatisticController {

    private final StatisticServiceImpl statisticService;

    @Autowired
    public ApiStatisticController(StatisticServiceImpl statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllStat(Principal principal) {
        if (principal == null) {
            return statisticService.getAllStatisticNonAuth();
        }
        return statisticService.getAllStatistic(principal.getName());
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> getMyStat(Principal principal) {
        return statisticService.getMyStatistic(principal.getName());
    }
}
