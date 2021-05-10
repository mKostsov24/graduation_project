package org.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class CalendarDTO {
    private List<Integer> years;
    private Map<String, Long> posts;
}
