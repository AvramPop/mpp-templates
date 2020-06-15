package org.example.server.repository;

import org.example.server.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
  @Query("select s.name from Sensor s")
  List<String> findAllNames();

  @Query(value = "select * from Sensor where name=:nameParam order by time desc limit 4", nativeQuery = true)
  List<Sensor> findFirstFourForName(@Param("nameParam") String nameParam);
}
