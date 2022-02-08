package com.example.demo.entity;

import com.example.demo.i18n.TestError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

/**
 * @author humbinal
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotBlank(message = "{" + TestError.USERNAME_NOT_BLANK + "}")
    private String name;

    @Range(min = 0, max = 200, message = "{" + TestError.USER_AGE_RANGE + "}")
    private Integer age;
}
