package org.techtricks.artisanPlatform.services;

import org.springframework.stereotype.Service;
import org.techtricks.artisanPlatform.dto.ArtisanDTO;
import org.techtricks.artisanPlatform.exceptions.ArtisanAlreadyExistsException;
import org.techtricks.artisanPlatform.exceptions.ArtisanNotFoundException;
import org.techtricks.artisanPlatform.models.Artisan;


import java.util.List;
import java.util.Optional;

@Service
public interface ArtisanService {


    public List<ArtisanDTO> getAllArtisan();

    public Artisan saveArtisan(Artisan artisan) throws ArtisanAlreadyExistsException;


    public Artisan getByUsername(String username) throws ArtisanNotFoundException;

    public Artisan getByEmail(String email) throws ArtisanNotFoundException;

    boolean deleteByEmail(String email);

    public Artisan updateArtisan(Artisan artisan) throws ArtisanNotFoundException;


    public Optional<Artisan >authenticate(String email, String password) throws ArtisanNotFoundException;
}
