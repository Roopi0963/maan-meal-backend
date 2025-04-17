package org.techtricks.maanmeal.services;

import org.springframework.stereotype.Service;
import org.techtricks.maanmeal.exceptions.FarmerNotFoundException;
import org.techtricks.maanmeal.exceptions.AttendanceAlreadyMarkedException;
import org.techtricks.maanmeal.models.Attendance;

import java.util.List;
import java.util.Map;

@Service
public interface AttendanceService {

    Attendance markAttendance(Long artisanId , boolean present) throws AttendanceAlreadyMarkedException, FarmerNotFoundException;

    List<Attendance> getAttendanceHistory(Long artisanId) throws FarmerNotFoundException;

    double getTotalEarnings(Long artisanId) throws FarmerNotFoundException;

    List<Object[]> getAllArtisansData();

    public Map<String, Map<String, Integer>> getDailySummary();
}
