package org.techtricks.maanmeal.services;

import org.springframework.stereotype.Service;
import org.techtricks.maanmeal.dto.AddressDTO;
import org.techtricks.maanmeal.exceptions.AddressNotFoundException;
import org.techtricks.maanmeal.exceptions.UserNotFoundException;
import org.techtricks.maanmeal.models.Address;

import java.util.List;

@Service
public interface AddressService {

    public Address addAddress(Long userId, Address address) throws UserNotFoundException;

    public List<AddressDTO> getAddressesByUserId(Long userId);

    public Address updateAddress(Long addressId, Address updatedAddress) throws AddressNotFoundException;

    public void deleteAddress(Long addressId) throws AddressNotFoundException;
}
