package org.techtricks.maanmeal.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.techtricks.maanmeal.dto.FarmerDTO;
import org.techtricks.maanmeal.exceptions.FarmerAlreadyExistsException;
import org.techtricks.maanmeal.exceptions.FarmerNotFoundException;
import org.techtricks.maanmeal.models.Farmer;
import org.techtricks.maanmeal.services.FarmerService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/farmer")
public class FarmerController {

    private final FarmerService farmerService;

    public FarmerController(FarmerService farmerService) {
        this.farmerService = farmerService;
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerArtisan(@RequestBody Farmer farmer) {
        try {
            Farmer registeredFarmer = farmerService.saveArtisan(farmer);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Artisan registered successfully", "artisan", registeredFarmer));
        } catch (FarmerAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Something went wrong. Please try again."));
        }
    }


    @GetMapping("/farmers")
    public ResponseEntity<List<FarmerDTO>> getAllArtisans() {
        List<FarmerDTO> data = farmerService.getAllArtisan();
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/getByUserName")
    public ResponseEntity<Farmer> getByUsername(@RequestParam("username") String username) throws FarmerNotFoundException {
        Farmer farmer = farmerService.getByUsername(username);
        return ResponseEntity.ok(farmer);
    }

    @GetMapping("/getByEmail")
    public ResponseEntity<Farmer> getByEmail(@RequestParam("email") String email) throws FarmerNotFoundException {
        Farmer farmer = farmerService.getByEmail(email);
        return ResponseEntity.ok(farmer);
    }

    @DeleteMapping("/deleteAccount")
    public boolean deleteByEmail(@RequestParam String email) throws FarmerNotFoundException {
        Farmer farmer = farmerService.getByEmail(email);
        return farmerService.deleteByEmail(email);

    }
    @PutMapping("/update")
    public ResponseEntity<?> updateArtisan(@RequestBody Farmer farmer) throws FarmerNotFoundException {
        Farmer updateFarmer = farmerService.updateArtisan(farmer);
        return ResponseEntity.ok(updateFarmer);

    }
}
