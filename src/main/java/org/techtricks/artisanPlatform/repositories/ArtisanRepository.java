package org.techtricks.artisanPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.techtricks.artisanPlatform.dto.ArtisanDTO;
import org.techtricks.artisanPlatform.models.Artisan;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtisanRepository extends JpaRepository<Artisan, Long> {
    Optional<Artisan> findByEmail(String email);

    Optional<Artisan> findByUserName(String username);

    @Query("SELECT new org.techtricks.artisanPlatform.dto.ArtisanDTO(a.id,a.userName, a.skill) FROM Artisan a")
    List<ArtisanDTO> findAllNameAndSkill();
}