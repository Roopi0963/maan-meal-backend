package org.techtricks.maanmeal.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.techtricks.maanmeal.dto.FarmerDTO;
import org.techtricks.maanmeal.exceptions.FarmerAlreadyExistsException;
import org.techtricks.maanmeal.exceptions.FarmerNotFoundException;
import org.techtricks.maanmeal.models.Farmer;
import org.techtricks.maanmeal.repositories.FarmerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FarmerServiceImpl implements FarmerService {

    private final FarmerRepository artisanRepository;

    private final PasswordEncoder passwordEncoder;

    public FarmerServiceImpl(FarmerRepository artisanRepository, PasswordEncoder passwordEncoder) {
        this.artisanRepository = artisanRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<FarmerDTO> getAllArtisan() {
        return artisanRepository.findAllNameAndSkill();

    }

    @Override
    public Farmer saveArtisan(Farmer farmer) throws FarmerAlreadyExistsException {
        Optional<Farmer> artisanOptional = artisanRepository.findByEmail(farmer.getEmail());
        if(artisanOptional.isPresent()) {
            throw new FarmerAlreadyExistsException("Artisan already exists with this email:"+ farmer.getEmail() +",please login with another email");
        }
        farmer.setPassword(passwordEncoder.encode(farmer.getPassword()));
        return artisanRepository.save(farmer);
    }

    @Override
    public Farmer getByUsername(String username) throws FarmerNotFoundException {
        Optional<Farmer> artisanOptional = artisanRepository.findByUserName(username);
        if(artisanOptional.isEmpty())
            throw new FarmerNotFoundException("Artisan not found on this username "+username+" , please try another email");
        return artisanOptional.get();
    }

    @Override
    public Farmer getByEmail(String email) throws FarmerNotFoundException {
        Optional<Farmer> artisanOptional =  artisanRepository.findByEmail(email);
        if(artisanOptional.isEmpty())
            throw new FarmerNotFoundException("Artisan not found on this Gmail "+email);
        return artisanOptional.get();
    }

    @Override
    public boolean deleteByEmail(String email) {
        Optional<Farmer> optionalArtisan = artisanRepository.findByEmail(email);
        if(optionalArtisan.isPresent()) {
            artisanRepository.delete(optionalArtisan.get());
            return true;
        }
        return false;
    }

    @Override
    public Farmer updateArtisan(Farmer farmer) throws FarmerNotFoundException {
        return artisanRepository.findByEmail(farmer.getEmail())
                .map(existingArtisan -> {
                    existingArtisan.setName(farmer.getName());
                    existingArtisan.setUserName(farmer.getUserName());
                    existingArtisan.setEmail(farmer.getEmail());
                    existingArtisan.setPassword(passwordEncoder.encode(farmer.getPassword()));
                    existingArtisan.setSkill(farmer.getSkill());
                    existingArtisan.setLocation(farmer.getLocation());
                    existingArtisan.setArtisanRating(farmer.getArtisanRating());
                    return artisanRepository.save(existingArtisan);
                })
                .orElseThrow(() -> new FarmerNotFoundException("Artisan not found with email: " + farmer.getEmail()));
    }

    @Override
    public  Optional<Farmer>  authenticate(String email, String password)  {
        Optional<Farmer> optionalArtisan = artisanRepository.findByEmail(email);
        if(optionalArtisan.isPresent() && passwordEncoder.matches(password, optionalArtisan.get().getPassword())) {
            return optionalArtisan;
        }
        return Optional.empty();
    }
}
