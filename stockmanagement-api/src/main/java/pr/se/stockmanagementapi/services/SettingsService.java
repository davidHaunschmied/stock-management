package pr.se.stockmanagementapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pr.se.stockmanagementapi.exceptions.AppException;
import pr.se.stockmanagementapi.model.Settings;
import pr.se.stockmanagementapi.payload.SettingsChangeRequest;
import pr.se.stockmanagementapi.respository.SettingsRepository;

@Service
public class SettingsService {

    private SettingsRepository settingsRepository;

    @Autowired
    public SettingsService(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public Settings getSettings() {
        return settingsRepository.findById(Settings.SETTINGS_ID).orElseThrow(() -> new AppException("Settings does not exist!"));
    }

    public Settings changeSettings(SettingsChangeRequest settingsChangeRequest) {
        Settings settings = getSettings();
        settings.setFlatSellCharges(settingsChangeRequest.getFlatSellCharges());
        settings.setFlatPurchaseCharges(settingsChangeRequest.getFlatPurchaseCharges());
        settings.setRelativeSellCharges(settingsChangeRequest.getRelativeSellCharges());
        settings.setRelativePurchaseCharges(settingsChangeRequest.getRelativePurchaseCharges());
        return settingsRepository.save(settings);
    }
}
