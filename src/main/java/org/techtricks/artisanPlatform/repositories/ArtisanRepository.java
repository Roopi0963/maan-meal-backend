package org.techtricks.artisanPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.techtricks.artisanPlatform.models.Artisan;

import java.util.Optional;

@Repository
public interface ArtisanRepository extends JpaRepository<Artisan, Long> {
    Optional<Artisan> findByEmail(String email);

    Optional<Artisan> findByUserName(String username);
}