package org.example.controller;

import org.example.model.dto.settings.NewGlobalSettingsDTO;
import org.example.service.SettingServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/settings")
public class ApiSettingController {

    private final SettingServiceImpl settingsService;

    public ApiSettingController(SettingServiceImpl settingsService) {
        this.settingsService = settingsService;
    }

    @PutMapping
    @PreAuthorize("hasAuthority('user:moder')")
    public void saveSettings(@RequestBody NewGlobalSettingsDTO globalSettingsDTO) {
        settingsService.saveGlobalSettings(globalSettingsDTO);
    }
}
