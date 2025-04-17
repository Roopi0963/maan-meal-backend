package org.techtricks.maanmeal.services;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.techtricks.maanmeal.exceptions.FarmerNotFoundException;
import org.techtricks.maanmeal.exceptions.AttendanceAlreadyMarkedException;
import org.techtricks.maanmeal.models.Farmer;
import org.techtricks.maanmeal.models.Attendance;
import org.techtricks.maanmeal.repositories.FarmerRepository;
import org.techtricks.maanmeal.repositories.AttendanceRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final FarmerRepository artisanRepository;
    private final FarmerServiceImpl farmerServiceImpl;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, FarmerRepository artisanRepository, FarmerServiceImpl farmerServiceImpl) {
        this.attendanceRepository = attendanceRepository;
        this.artisanRepository = artisanRepository;
        this.farmerServiceImpl = farmerServiceImpl;
    }



    @Override
    public Attendance markAttendance(Long artisanId, boolean present) throws AttendanceAlreadyMarkedException, FarmerNotFoundException {
        Optional<Farmer> artisanOptional = artisanRepository.findById(artisanId);
        if (artisanOptional.isPresent()) {
            Farmer farmer = artisanOptional.get();
            LocalDate today = LocalDate.now();

            Optional<Attendance> existingAttendance = attendanceRepository.findByFarmerIdAndDate(artisanId, today);
            if (existingAttendance.isPresent()) {
                throw new AttendanceAlreadyMarkedException("Attendance already marked for today.");
            }

            Attendance attendance = new Attendance();
            attendance.setDate(today);
            attendance.setPresent(present);
            attendance.setFarmer(farmer);



            if (present) {
                farmer.setTotalWorkDays(farmer.getTotalWorkDays() + 1);
                farmer.calculateTotalEarnings();
            }
            artisanRepository.save(farmer);
            return attendanceRepository.save(attendance);
        } else {
            throw new FarmerNotFoundException("Artisan not found with ID: " + artisanId);
        }
    }

    @Override
    @Transactional
    public List<Attendance> getAttendanceHistory(Long artisanId) throws FarmerNotFoundException {
        if (artisanRepository.findById(artisanId).isPresent()) {
            return attendanceRepository.findByFarmerId(artisanId);
        }else {
            throw new FarmerNotFoundException("Artisan Not found with :"+artisanId);
        }
    }

    @Override
    public double getTotalEarnings(Long artisanId) throws FarmerNotFoundException {
        Optional<Farmer> artisanOptional = artisanRepository.findById(artisanId);
        if(artisanOptional.isPresent()) {
            Farmer farmer = artisanOptional.get();
            return farmer.getTotalEarnings();
        }else{
            throw new FarmerNotFoundException("artisan not found on this id:"+artisanId);
        }
    }

    @Override
    public Map<String, Map<String, Integer>> getDailySummary() {
        List<Attendance> all = attendanceRepository.findAll();

        return all.stream().collect(Collectors.groupingBy(
                att -> att.getDate().toString(), // group by date
                Collectors.collectingAndThen(Collectors.toList(), list -> {
                    int presentCount = (int) list.stream().filter(Attendance::isPresent).count();
                    int absentCount = list.size() - presentCount;
                    Map<String, Integer> countMap = new HashMap<>();
                    countMap.put("present", presentCount);
                    countMap.put("absent", absentCount);
                    return countMap;
                })
        ));
    }


    @Override
    public List<Object[]> getAllArtisansData() {
        return  artisanRepository.findAll().stream()
                .map(artisan -> new Object[]{
                        artisan.getId(),
                        artisan.getUserName(),
                        artisan.getTotalWorkDays(),
                        artisan.getTotalEarnings(),
                })
                .toList();
    }


}
