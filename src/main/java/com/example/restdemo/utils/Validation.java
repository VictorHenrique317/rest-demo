package com.example.restdemo.utils;

import com.example.restdemo.common.EntityNotFoundException;
import org.springframework.data.domain.Page;

public class Validation {
    public static Object defineResponse(Object result) {
        if (result == null) throw new EntityNotFoundException();
        return result;
    }

    public static Object defineResponse(Page<Object> page) {
        if (page == null) throw new EntityNotFoundException();
        return page;
    }
}
