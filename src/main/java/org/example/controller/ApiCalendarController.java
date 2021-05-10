package org.example.controller;

import org.example.model.dto.CalendarDTO;
import org.example.service.CalendarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calendar")
public class ApiCalendarController {

    private final CalendarServiceImpl calendarService;

    @Autowired
    public ApiCalendarController(CalendarServiceImpl calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping
    public CalendarDTO getCalendar(@RequestParam(name = "year", defaultValue = "2021") String year) {
        return calendarService.getCalendar(year);
    }
}
