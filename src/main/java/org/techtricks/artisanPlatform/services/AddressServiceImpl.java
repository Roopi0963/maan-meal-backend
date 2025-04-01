package org.techtricks.artisanPlatform.services;

import org.springframework.stereotype.Service;
import org.techtricks.artisanPlatform.dto.AddressDTO;
import org.techtricks.artisanPlatform.exceptions.AddressNotFoundException;
import org.techtricks.artisanPlatform.exceptions.UserNotFoundException;
import org.techtricks.artisanPlatform.models.Address;
import org.techtricks.artisanPlatform.models.User;
import org.techtricks.artisanPlatform.repositories.AddressRepository;
import org.techtricks.artisanPlatform.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {


    private final AddressRepository addressRepository;


    private final UserRepository userRepository;

    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    // Add Address
    public Address addAddress(Long userId, Address address) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            address.setUser(userOptional.get());
            return addressRepository.save(address);
        } else {
            throw new UserNotFoundException("User not found with ID: "+ userId);
        }
    }

    // Get All Addresses for a User
    @Override
    public List<AddressDTO> getAddressesByUserId(Long userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        return addresses.stream()
                .map(address -> new AddressDTO(
                        address.getId(),
                        address.getStreet(),
                        address.getCity(),
                        address.getState(),
                        address.getZipCode(),
                        address.getCountry()
                ))
                .toList();
    }


    // Update Address
    public Address updateAddress(Long addressId, Address updatedAddress) throws AddressNotFoundException {
        Optional<Address> addressOptional = addressRepository.findById(addressId);
        if (addressOptional.isPresent()) {
            Address existingAddress = addressOptional.get();
            existingAddress.setStreet(updatedAddress.getStreet());
            existingAddress.setCity(updatedAddress.getCity());
            existingAddress.setState(updatedAddress.getState());
            existingAddress.setZipCode(updatedAddress.getZipCode());
            existingAddress.setCountry(updatedAddress.getCountry());
            return addressRepository.save(existingAddress);
        } else {
            throw new AddressNotFoundException("Address not Found with Id:"+addressId);
        }
    }

    // Delete Address
    public void deleteAddress(Long addressId) throws AddressNotFoundException {
        if (!addressRepository.existsById(addressId)) {
            throw new AddressNotFoundException("Address not found With Id: "+addressId);
        }
        addressRepository.deleteById(addressId);
    }
}
