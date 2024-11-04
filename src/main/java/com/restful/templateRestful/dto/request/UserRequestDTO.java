package com.restful.templateRestful.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restful.templateRestful.util.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.restful.templateRestful.util.Gender.FEMALE;
import static com.restful.templateRestful.util.Gender.MALE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestDTO implements Serializable {

    @NotBlank(message = " firstName must be not blank")
    String firstName;

    @NotNull(message = "lastName must be not null")
    String lastName;

    //    @Pattern(regexp = "^\\d{10}", message = "Phone invalid format")
    @PhoneNumber(message = "phone must be invalid format")
    String phone;

    @Email
    String email;

    @NotNull(message = "dateOfBirth must not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    Date dateOfBirth;

    @EnumPattern(name = "status", regexp = "ACTIVE|INACTIVE|NONE")
    UserStatus status;

    @GenderSubset(anyOf = {MALE, FEMALE})
    Gender gender;

    @NotNull(message = "username must be not null")
    String username;

    String password;

    @NotNull(message = "type must be not null")
    @EnumValue(name = "type", enumClass = UserType.class)
    String type;

    @NotEmpty(message = "addresses can not empty")
    Set<AddressDTO> addresses;

    @EnumPattern(name = "status", regexp = "ACTIVE|INACTIVE")
    UserStatus statusMethod;

    public UserRequestDTO(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
}
