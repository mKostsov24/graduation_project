package org.example.service;

import org.example.model.dto.CalendarDTO;
import org.example.repository.CalendarRepository;
import org.example.service.api.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class CalendarServiceImpl implements CalendarService {
    private final CalendarRepository calendarRepository;

    @Autowired
    public CalendarServiceImpl(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    private static Map<String, Long> getSort(Map<String, Long> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public CalendarDTO getCalendar(String year) {
        List<Integer> years =
                calendarRepository.findAllYears()
                        .stream()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

        Map<String, Long> calendarDateCount =
                calendarRepository.findAllPostsByYear(year)
                        .stream()
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return new CalendarDTO(years, getSort(calendarDateCount));
    }
}
