package org.techtricks.maanmeal.services;

import org.springframework.stereotype.Service;
import org.techtricks.maanmeal.dto.FarmerDTO;
import org.techtricks.maanmeal.exceptions.FarmerAlreadyExistsException;
import org.techtricks.maanmeal.exceptions.FarmerNotFoundException;
import org.techtricks.maanmeal.models.Farmer;


import java.util.List;
import java.util.Optional;

@Service
public interface FarmerService {


    public List<FarmerDTO> getAllArtisan();

    public Farmer saveArtisan(Farmer farmer) throws FarmerAlreadyExistsException;


    public Farmer getByUsername(String username) throws FarmerNotFoundException;

    public Farmer getByEmail(String email) throws FarmerNotFoundException;

    boolean deleteByEmail(String email);

    public Farmer updateArtisan(Farmer farmer) throws FarmerNotFoundException;


    public Optional<Farmer>authenticate(String email, String password) throws FarmerNotFoundException;
}
