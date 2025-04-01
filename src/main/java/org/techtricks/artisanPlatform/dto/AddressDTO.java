package org.techtricks.artisanPlatform.dto;
import lombok.Data;


@Data
public class AddressDTO {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    public AddressDTO(Long id, String street, String city, String state, String zipCode, String country) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }
}
