package org.techtricks.maanmeal.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.techtricks.maanmeal.dto.AddressDTO;
import org.techtricks.maanmeal.exceptions.AddressNotFoundException;
import org.techtricks.maanmeal.exceptions.UserNotFoundException;
import org.techtricks.maanmeal.models.Address;
import org.techtricks.maanmeal.services.AddressService;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // Add Address
    @PostMapping("/{userId}")
    public ResponseEntity<AddressDTO> addAddress(@PathVariable Long userId, @RequestBody Address address) throws UserNotFoundException {
        Address savedAddress = addressService.addAddress(userId, address);
        AddressDTO addressDTO = new AddressDTO(
                savedAddress.getId(),
                savedAddress.getStreet(),
                savedAddress.getCity(),
                savedAddress.getState(),
                savedAddress.getZipCode(),
                savedAddress.getCountry()
        );
        return ResponseEntity.ok(addressDTO);
    }

    // Get All Addresses for a User
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressDTO>> getUserAddresses(@PathVariable Long userId) {
        List<AddressDTO> addressDTOs = addressService.getAddressesByUserId(userId);
        return ResponseEntity.ok(addressDTOs);
    }

    // Update Address
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId, @RequestBody Address address) throws AddressNotFoundException {
        Address updatedAddress = addressService.updateAddress(addressId, address);
        AddressDTO addressDTO = new AddressDTO(
                updatedAddress.getId(),
                updatedAddress.getStreet(),
                updatedAddress.getCity(),
                updatedAddress.getState(),
                updatedAddress.getZipCode(),
                updatedAddress.getCountry()
        );
        return ResponseEntity.ok(addressDTO);
    }

    // Delete Address
    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId) throws AddressNotFoundException {
        addressService.deleteAddress(addressId);
        return ResponseEntity.ok("Address deleted successfully.");
    }
}
