package org.techtricks.artisanPlatform.services;

import org.springframework.stereotype.Service;
import org.techtricks.artisanPlatform.dto.AddressDTO;
import org.techtricks.artisanPlatform.exceptions.AddressNotFoundException;
import org.techtricks.artisanPlatform.exceptions.UserNotFoundException;
import org.techtricks.artisanPlatform.models.Address;

import java.util.List;

@Service
public interface AddressService {

    public Address addAddress(Long userId, Address address) throws UserNotFoundException;

    public List<AddressDTO> getAddressesByUserId(Long userId);

    public Address updateAddress(Long addressId, Address updatedAddress) throws AddressNotFoundException;

    public void deleteAddress(Long addressId) throws AddressNotFoundException;
}
