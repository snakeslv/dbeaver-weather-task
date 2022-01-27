package com.dbeaver.weather.repository;

import com.dbeaver.weather.model.Weather;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, LocalDate> {

  Optional<Weather> getByDate(LocalDate date);
}
