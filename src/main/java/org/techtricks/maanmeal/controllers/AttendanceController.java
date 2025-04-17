package org.techtricks.maanmeal.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.techtricks.maanmeal.exceptions.FarmerNotFoundException;
import org.techtricks.maanmeal.exceptions.AttendanceAlreadyMarkedException;
import org.techtricks.maanmeal.models.Attendance;
import org.techtricks.maanmeal.services.AttendanceService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
//@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }


    // AttendanceController.java

    @GetMapping("/daily-summary")
    public ResponseEntity<Map<String, Map<String, Integer>>> getDailyAttendanceSummary() {
        Map<String, Map<String, Integer>> summary = attendanceService.getDailySummary();
        return ResponseEntity.ok(summary);
    }

    // ✅ 1. Mark attendance
    @PostMapping("/mark/{artisanId}")
    public ResponseEntity<Attendance> markAttendance(
            @PathVariable Long artisanId,
            @RequestParam boolean present
    ) {
        try {
            Attendance attendance = attendanceService.markAttendance(artisanId, present);
            return ResponseEntity.ok(attendance);
        } catch (AttendanceAlreadyMarkedException | FarmerNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // ✅ 2. Get attendance history of artisan
    @GetMapping("/history/{artisanId}")
    public ResponseEntity<List<Attendance>> getAttendanceHistory(@PathVariable Long artisanId) {
        try {
            List<Attendance> history = attendanceService.getAttendanceHistory(artisanId);
            return ResponseEntity.ok(history);
        } catch (FarmerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ 3. Get total earnings of artisan
    @GetMapping("/earnings/{artisanId}")
    public ResponseEntity<Double> getTotalEarnings(@PathVariable Long artisanId) {
        try {
            double earnings = attendanceService.getTotalEarnings(artisanId);
            return ResponseEntity.ok(earnings);
        } catch (FarmerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ 4. Get all artisans data for admin dashboard table
    @GetMapping("/admin/all")
    public ResponseEntity<List<Object[]>> getAllArtisansData() {
        List<Object[]> allData = attendanceService.getAllArtisansData();
        return ResponseEntity.ok(allData);
    }
}
