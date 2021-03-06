package com.dbeaver.weather.controller;

import com.dbeaver.weather.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

  private final WeatherService weatherService;

  public WeatherController(WeatherService weatherService) {
    this.weatherService = weatherService;
  }


  @GetMapping("/weather")
  public String getWeather() {
    return weatherService.getCurrentWeather();
  }
}
