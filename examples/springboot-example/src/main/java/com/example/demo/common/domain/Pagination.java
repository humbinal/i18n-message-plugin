package com.example.demo.common.domain;

import lombok.Data;

/**
 * @author humbinal
 */
@Data
public class Pagination {

    private Integer pageNo;

    private Integer pageSize;

    private Integer totalPage;

    private Long total;

    private Pagination() {
    }

    public static Pagination paging(Integer pageNo, Integer pageSize) {
        Pagination pagination = new Pagination();
        pagination.setPageNo(pageNo);
        pagination.setPageSize(pageSize);
        return pagination;
    }
}
