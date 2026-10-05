package com.example.autoloc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(String name, int maxRentals, String supportEmail) {
}
