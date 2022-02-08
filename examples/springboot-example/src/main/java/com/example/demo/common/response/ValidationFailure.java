package com.example.demo.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author humbinal
 */
@Data
@AllArgsConstructor
public class ValidationFailure {
    private String name;
    private String value;
}
