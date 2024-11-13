package com.restful.templateRestful.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageResponse<T> {
    private int page;
    private int size;
    private long total;
    private T items;
}
