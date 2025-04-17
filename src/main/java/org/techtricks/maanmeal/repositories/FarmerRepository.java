package org.techtricks.maanmeal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.techtricks.maanmeal.dto.FarmerDTO;
import org.techtricks.maanmeal.models.Farmer;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    Optional<Farmer> findByEmail(String email);

    Optional<Farmer> findByUserName(String username);

    @Query("SELECT new org.techtricks.maanmeal.dto.FarmerDTO(a.id,a.userName, a.skill) FROM Farmer a")
    List<FarmerDTO> findAllNameAndSkill();
}