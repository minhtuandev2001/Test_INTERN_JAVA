package com.minhtuan.internjava.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse <T>{
    int page;
    int pageSize;
    int totalPage;
    Long totalElement;
    T items;
}
