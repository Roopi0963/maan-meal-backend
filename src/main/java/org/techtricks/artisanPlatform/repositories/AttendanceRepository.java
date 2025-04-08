package org.techtricks.artisanPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.techtricks.artisanPlatform.dto.ArtisanDTO;
import org.techtricks.artisanPlatform.models.Attendance;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance , Long> {
    Optional<Attendance> findByArtisanIdAndDate(Long artisanId, LocalDate date);

    List<Attendance> findByArtisanId(Long artisanId);




}
