package org.techtricks.artisanPlatform.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.techtricks.artisanPlatform.exceptions.ArtisanAlreadyExistsException;
import org.techtricks.artisanPlatform.exceptions.ArtisanNotFoundException;
import org.techtricks.artisanPlatform.models.Artisan;
import org.techtricks.artisanPlatform.services.ArtisanService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/artisan")
public class ArtisanController {

    private final ArtisanService artisanService;

    public ArtisanController(ArtisanService artisanService) {
        this.artisanService = artisanService;
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerArtisan(@RequestBody Artisan artisan) {
        try {
            Artisan registeredArtisan = artisanService.saveArtisan(artisan);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Artisan registered successfully", "artisan", registeredArtisan));
        } catch (ArtisanAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Something went wrong. Please try again."));
        }
    }


    @GetMapping("/artisans")
    public ResponseEntity<List<Artisan>> getAllArtisans() {
        List<Artisan> artisanList = artisanService.getAllArtisan();
        return ResponseEntity.ok(artisanList);
    }

    @GetMapping("/getByUserName")
    public ResponseEntity<Artisan> getByUsername(@RequestParam("username") String username) throws ArtisanNotFoundException {
        Artisan artisan = artisanService.getByUsername(username);
        return ResponseEntity.ok(artisan);
    }

    @GetMapping("/getByEmail")
    public ResponseEntity<Artisan> getByEmail(@RequestParam("email") String email) throws ArtisanNotFoundException {
        Artisan artisan = artisanService.getByEmail(email);
        return ResponseEntity.ok(artisan);
    }

    @DeleteMapping("/deleteAccount")
    public boolean deleteByEmail(@RequestParam String email) throws ArtisanNotFoundException {
        Artisan artisan = artisanService.getByEmail(email);
        return artisanService.deleteByEmail(email);

    }
    @PutMapping("/update")
    public ResponseEntity<?> updateArtisan(@RequestBody Artisan artisan) throws ArtisanNotFoundException {
        Artisan updateArtisan = artisanService.updateArtisan(artisan);
        return ResponseEntity.ok(updateArtisan);

    }
}
