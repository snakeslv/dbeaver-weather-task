package com.dbeaver.weather.service;

import com.dbeaver.weather.model.Weather;
import com.dbeaver.weather.repository.WeatherRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

  private final WeatherRepository weatherRepository;

  public WeatherService(WeatherRepository weatherRepository) {
    this.weatherRepository = weatherRepository;
  }

  public String getCurrentWeather() {
    if (weatherRepository.getByDate(LocalDate.now()).isEmpty()) {
      insertParsedWeather(parseWeather());
    }
    return weatherRepository.getByDate(LocalDate.now()).get().getTemperature();
  }

  private String parseWeather() {
    String result = "";
    try (BufferedReader input = new BufferedReader(new InputStreamReader(
        new URL("https://yandex.ru").openConnection().getInputStream()))) {
      String str;
      Pattern pattern = Pattern.compile("weather__temp.+?(?=<)");
      while ((str = input.readLine()) != null) {
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
          result = matcher.group();
          break;
        }
      }
      return result.substring(result.indexOf(">") + 1);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  private void insertParsedWeather(String temperature) {
    Weather weather = new Weather();
    weather.setDate(LocalDate.now());
    weather.setTemperature(temperature);
    weatherRepository.save(weather);
  }
}
