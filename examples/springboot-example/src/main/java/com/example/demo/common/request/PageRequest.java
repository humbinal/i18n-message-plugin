package com.example.demo.common.request;

import com.example.demo.common.i18n.CommonError;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @author humbinal
 */
@Data
public class PageRequest {

    @Min(value = 1, message = "{" + CommonError.VALIDATION_PAGE_NO_MIN + "}")
    private Integer pageNo = 1;

    @Min(value = 1, message = "{" + CommonError.VALIDATION_PAGE_SIZE_MIN + "}")
    private Integer pageSize = 10;
}
