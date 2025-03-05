package org.techtricks.artisanPlatform.services;

import org.springframework.stereotype.Service;
import org.techtricks.artisanPlatform.exceptions.ArtisanAlreadyExistsException;
import org.techtricks.artisanPlatform.exceptions.ArtisanNotFoundException;
import org.techtricks.artisanPlatform.models.Artisan;


import java.util.List;

@Service
public interface ArtisanService {


    public List<Artisan> getAllArtisan();

    public Artisan saveArtisan(Artisan artisan) throws ArtisanAlreadyExistsException;


    public Artisan getByUsername(String username) throws ArtisanNotFoundException;

}
