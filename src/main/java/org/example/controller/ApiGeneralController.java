package org.example.controller;

import org.example.model.dto.settings.GlobalSettingsDTO;
import org.example.response.InitResponse;
import org.example.service.SettingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class ApiGeneralController {

    private final SettingServiceImpl settingsService;
    private final InitResponse initResponse;

    @Autowired
    public ApiGeneralController(SettingServiceImpl settingsService, InitResponse initResponse) {
        this.settingsService = settingsService;
        this.initResponse = initResponse;
    }

    @GetMapping("settings")
    private GlobalSettingsDTO settings() {
        return settingsService.getGlobalSettings();
    }

    @GetMapping(value = "init", produces = MediaType.APPLICATION_JSON_VALUE)
    private InitResponse init() {
        return initResponse;
    }

}
