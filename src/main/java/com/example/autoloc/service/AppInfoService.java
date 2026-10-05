package com.example.autoloc.service;

import com.example.autoloc.config.AppProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AppInfoService {

    private final AppProperties props;

    // Single value with a default, using @Value
    @Value("${app.welcome-message:Bienvenue sur Autoloc}")
    private String welcomeMessage;

    // Grouped values, using constructor injection of @ConfigurationProperties
    public AppInfoService(AppProperties props) {
        this.props = props;
    }

    public boolean canRent(int currentRentals) {
        return currentRentals < props.maxRentals();
    }

    public String describe() {
        return welcomeMessage + " (" + props.name() + ", contact: " + props.supportEmail() + ")";
    }
}
