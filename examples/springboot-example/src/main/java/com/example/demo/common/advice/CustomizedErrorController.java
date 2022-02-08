package com.example.demo.common.advice;

import com.example.demo.common.i18n.CommonError;
import com.example.demo.common.response.CommonResponse;
import com.example.demo.common.utils.ResponseUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author humbinal
 */
@RestController
public class CustomizedErrorController extends BasicErrorController {

    public CustomizedErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes, new ErrorProperties());
    }

    /**
     * For 404 NOT FOUND
     */
    @Override
    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NOT_FOUND) {
            CommonResponse<Object> commonResponse = ResponseUtils.error(CommonError.NOT_FOUND);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> objectMap = objectMapper.convertValue(commonResponse, new TypeReference<>() {
            });
            return new ResponseEntity<>(objectMap, status);
        } else {
            return super.error(request);
        }
    }

}
