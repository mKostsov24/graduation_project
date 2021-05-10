package org.example.service;

import org.example.model.GlobalSettings;
import org.example.model.dto.settings.GlobalSettingsDTO;
import org.example.model.dto.settings.NewGlobalSettingsDTO;
import org.example.repository.SettingRepository;
import org.example.service.api.SettingService;
import org.example.utils.ErrorsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl implements SettingService {
    private final Logger logger = LoggerFactory.getLogger("Logger");
    private final SettingRepository settingRepository;
    private GlobalSettingsDTO globalSettingsDTO;

    @Autowired
    public SettingServiceImpl(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    public GlobalSettingsDTO getGlobalSettings() {
        globalSettingsDTO = new GlobalSettingsDTO();
        settingRepository.findAll()
                .forEach(this::setSettingsValue);
        return globalSettingsDTO;
    }

    private void setSettingsValue(GlobalSettings settings) {
        switch (settings.getCode()) {
            case "MULTIUSER_MODE":
                globalSettingsDTO.setMultiuserMode(
                        getBooleanValue(settings.getValue()));
                break;
            case "POST_PREMODERATION":
                globalSettingsDTO.setPostPremoderation(
                        getBooleanValue(settings.getValue()));
                break;
            case "STATISTICS_IS_PUBLIC":
                globalSettingsDTO.setStatisticsIsPublic(
                        getBooleanValue(settings.getValue()));
                break;
            default:
                logger.info(ErrorsList.STRING_SETTING_NOT_FOUND);
                break;
        }
    }

    private boolean getBooleanValue(String settings) {
        BooleanValue value = () -> settings.equals("YES");
        return value.getBoolean();
    }

    public void saveGlobalSettings(NewGlobalSettingsDTO globalSettingsDTO) {
        if (globalSettingsDTO.isMultiuserMode()) {
            settingRepository.updateValueByCode("YES", "MULTIUSER_MODE");
        } else {
            settingRepository.updateValueByCode("NO", "MULTIUSER_MODE");
        }
        if (globalSettingsDTO.isPostPremoderation()) {
            settingRepository.updateValueByCode("YES", "POST_PREMODERATION");
        } else {
            settingRepository.updateValueByCode("NO", "POST_PREMODERATION");
        }
        if (globalSettingsDTO.isStatisticsIsPublic()) {
            settingRepository.updateValueByCode("YES", "STATISTICS_IS_PUBLIC");
        } else {
            settingRepository.updateValueByCode("NO", "STATISTICS_IS_PUBLIC");
        }
    }

    @FunctionalInterface
    private interface BooleanValue {
        boolean getBoolean();
    }
}