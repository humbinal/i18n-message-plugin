package com.example.demo.common.utils;

import com.example.demo.common.domain.Pagination;
import com.example.demo.common.i18n.CommonError;
import com.example.demo.common.response.CommonResponse;
import com.example.demo.common.response.ListResponse;
import com.example.demo.common.response.PageResponse;
import com.example.demo.common.spring.message.LocaleMessageUtils;

import java.util.List;

/**
 * @author humbinal
 */
public class ResponseUtils {

    public static <T> CommonResponse<T> success(T data) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(CommonError.SUCCESS);
        commonResponse.setMsg(LocaleMessageUtils.getMessage(CommonError.SUCCESS));
        commonResponse.setData(data);
        return commonResponse;
    }

    public static <T> CommonResponse<PageResponse<T>> page(List<T> list, Pagination pagination) {
        PageResponse<T> pageResponse = new PageResponse<>(list, pagination);
        CommonResponse<PageResponse<T>> commonResponse = new CommonResponse<>();
        commonResponse.setCode(CommonError.SUCCESS);
        commonResponse.setMsg(LocaleMessageUtils.getMessage(CommonError.SUCCESS));
        commonResponse.setData(pageResponse);
        return commonResponse;
    }

    public static <T> CommonResponse<ListResponse<T>> list(List<T> list) {
        ListResponse<T> listResponse = new ListResponse<>(list, (long) list.size());
        CommonResponse<ListResponse<T>> commonResponse = new CommonResponse<>();
        commonResponse.setCode(CommonError.SUCCESS);
        commonResponse.setMsg(LocaleMessageUtils.getMessage(CommonError.SUCCESS));
        commonResponse.setData(listResponse);
        return commonResponse;
    }

    public static <T> CommonResponse<T> error(String code) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(code);
        commonResponse.setMsg(LocaleMessageUtils.getMessage(code));
        commonResponse.setSolution(LocaleMessageUtils.getSolution(code));
        return commonResponse;
    }

    /**
     * error with data
     */
    public static <T> CommonResponse<T> error(String code, T data) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(code);
        commonResponse.setMsg(LocaleMessageUtils.getMessage(code));
        commonResponse.setSolution(LocaleMessageUtils.getSolution(code));
        commonResponse.setData(data);
        return commonResponse;
    }

}
