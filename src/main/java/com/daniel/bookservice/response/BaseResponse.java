package com.daniel.bookservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    private int statusCode;
    private String description;
    private Object data;
    private Object error;
}
