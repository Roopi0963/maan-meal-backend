package org.techtricks.maanmeal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.techtricks.maanmeal.models.Attendance;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance , Long> {
    Optional<Attendance> findByFarmerIdAndDate(Long farmerId, LocalDate date);

    List<Attendance> findByFarmerId(Long farmerId);




}
