package org.techtricks.artisanPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.techtricks.artisanPlatform.models.Address;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long userId);
}
