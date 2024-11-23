package com.restful.templateRestful.repository.criteria;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchCriteria {
    // dung dung de check search
    // firstName:hieu
    String key;  // firstName
    String operation; // : < >
    Object value; // hieu
}
