package com.restful.templateRestful.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDTO {

    String apartmentNumber;
    String floor;
    String building;
    String streetNumber;
    String street;
    String city;
    String country;
    Integer addressType;
}
