package org.techtricks.artisanPlatform.services;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.techtricks.artisanPlatform.exceptions.ArtisanNotFoundException;
import org.techtricks.artisanPlatform.exceptions.AttendanceAlreadyMarkedException;
import org.techtricks.artisanPlatform.models.Artisan;
import org.techtricks.artisanPlatform.models.Attendance;
import org.techtricks.artisanPlatform.repositories.ArtisanRepository;
import org.techtricks.artisanPlatform.repositories.AttendanceRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final ArtisanRepository artisanRepository;
    private final ArtisanServiceImpl artisanServiceImpl;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, ArtisanRepository artisanRepository, ArtisanServiceImpl artisanServiceImpl) {
        this.attendanceRepository = attendanceRepository;
        this.artisanRepository = artisanRepository;
        this.artisanServiceImpl = artisanServiceImpl;
    }



    @Override
    public Attendance markAttendance(Long artisanId, boolean present) throws AttendanceAlreadyMarkedException, ArtisanNotFoundException {
        Optional<Artisan> artisanOptional = artisanRepository.findById(artisanId);
        if (artisanOptional.isPresent()) {
            Artisan artisan = artisanOptional.get();
            LocalDate today = LocalDate.now();

            Optional<Attendance> existingAttendance = attendanceRepository.findByArtisanIdAndDate(artisanId, today);
            if (existingAttendance.isPresent()) {
                throw new AttendanceAlreadyMarkedException("Attendance already marked for today.");
            }

            Attendance attendance = new Attendance();
            attendance.setDate(today);
            attendance.setPresent(present);
            attendance.setArtisan(artisan);



            if (present) {
                artisan.setTotalWorkDays(artisan.getTotalWorkDays() + 1);
                artisan.calculateTotalEarnings();
            }
            artisanRepository.save(artisan);
            return attendanceRepository.save(attendance);
        } else {
            throw new ArtisanNotFoundException("Artisan not found with ID: " + artisanId);
        }
    }

    @Override
    @Transactional
    public List<Attendance> getAttendanceHistory(Long artisanId) throws ArtisanNotFoundException {
        if (artisanRepository.findById(artisanId).isPresent()) {
            return attendanceRepository.findByArtisanId(artisanId);
        }else {
            throw new ArtisanNotFoundException("Artisan Not found with :"+artisanId);
        }
    }

    @Override
    public double getTotalEarnings(Long artisanId) throws ArtisanNotFoundException {
        Optional<Artisan> artisanOptional = artisanRepository.findById(artisanId);
        if(artisanOptional.isPresent()) {
            Artisan artisan = artisanOptional.get();
            return artisan.getTotalEarnings();
        }else{
            throw new ArtisanNotFoundException("artisan not found on this id:"+artisanId);
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
