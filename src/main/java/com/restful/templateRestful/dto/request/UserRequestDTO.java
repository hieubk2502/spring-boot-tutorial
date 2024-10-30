package com.restful.templateRestful.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restful.templateRestful.util.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

import static com.restful.templateRestful.util.Gender.FEMALE;
import static com.restful.templateRestful.util.Gender.MALE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestDTO {

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

    @EnumPattern(name = "status",regexp = "ACTIVE|INACTIVE|NONE")
    UserStatus status;

    @EnumPattern(name = "status",regexp = "ACTIVE|INACTIVE")
    UserStatus statusMethod;

    @GenderSubset(anyOf = {MALE, FEMALE})
    Gender gender;

    @NotNull(message = "type must be not null")
    @EnumValue(name = "type", enumClass = UserType.class)
    String type;

    @NotEmpty
    List<String> permission;
}
