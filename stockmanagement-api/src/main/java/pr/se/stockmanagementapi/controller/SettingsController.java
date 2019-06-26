package pr.se.stockmanagementapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pr.se.stockmanagementapi.model.Settings;
import pr.se.stockmanagementapi.payload.SettingsChangeRequest;
import pr.se.stockmanagementapi.services.SettingsService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {
    private final SettingsService settingsService;

    @Autowired
    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }


    @GetMapping("/all")
    public Settings getSettings() {
        return settingsService.getSettings();
    }

    @PostMapping("/change")
    public ResponseEntity changeSettings(@Valid @RequestBody SettingsChangeRequest settingsChangeRequest) {
        return new ResponseEntity<>(settingsService.changeSettings(settingsChangeRequest), HttpStatus.OK);
    }
}
