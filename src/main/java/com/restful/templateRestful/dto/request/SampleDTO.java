package com.restful.templateRestful.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = false)
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
public class SampleDTO {


    @ToString.Include
    Integer id;

    @EqualsAndHashCode.Include
    @NonNull
    String name;


}
