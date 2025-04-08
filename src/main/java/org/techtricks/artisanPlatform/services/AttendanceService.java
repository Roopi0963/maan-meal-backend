package org.techtricks.artisanPlatform.services;

import org.springframework.stereotype.Service;
import org.techtricks.artisanPlatform.exceptions.ArtisanNotFoundException;
import org.techtricks.artisanPlatform.exceptions.AttendanceAlreadyMarkedException;
import org.techtricks.artisanPlatform.models.Attendance;

import java.util.List;
import java.util.Map;

@Service
public interface AttendanceService {

    Attendance markAttendance(Long artisanId , boolean present) throws AttendanceAlreadyMarkedException, ArtisanNotFoundException;

    List<Attendance> getAttendanceHistory(Long artisanId) throws ArtisanNotFoundException;

    double getTotalEarnings(Long artisanId) throws ArtisanNotFoundException;

    List<Object[]> getAllArtisansData();

    public Map<String, Map<String, Integer>> getDailySummary();
}
