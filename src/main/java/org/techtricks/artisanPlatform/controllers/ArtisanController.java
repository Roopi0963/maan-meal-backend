package org.techtricks.artisanPlatform.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.techtricks.artisanPlatform.exceptions.ArtisanAlreadyExistsException;
import org.techtricks.artisanPlatform.exceptions.ArtisanNotFoundException;
import org.techtricks.artisanPlatform.models.Artisan;
import org.techtricks.artisanPlatform.services.ArtisanService;

import java.util.List;

@RestController
@RequestMapping("/api/artisan")
public class ArtisanController {

    private final ArtisanService artisanService;

    public ArtisanController(ArtisanService artisanService) {
        this.artisanService = artisanService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Artisan> registerArtisan(@RequestBody Artisan artisan) throws ArtisanAlreadyExistsException {
        Artisan registerArtisan = artisanService.saveArtisan(artisan);
        return ResponseEntity.ok(registerArtisan);
    }


    @PostMapping("/artisans")
    public ResponseEntity<List<Artisan>> getAllArtisans() {
        List<Artisan> artisanList = artisanService.getAllArtisan();
        return ResponseEntity.ok(artisanList);
    }

    @GetMapping("/getByUserName")
    public ResponseEntity<Artisan> getByUsername(@RequestParam("username") String username) throws ArtisanNotFoundException {
        Artisan artisan = artisanService.getByUsername(username);
        return ResponseEntity.ok(artisan);
    }
}
