package org.techtricks.maanmeal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.techtricks.maanmeal.models.Address;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long userId);
}
