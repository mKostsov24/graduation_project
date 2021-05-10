package org.example.service.api;

import org.example.model.dto.CalendarDTO;

public interface CalendarService {
    CalendarDTO getCalendar(String year);
}
