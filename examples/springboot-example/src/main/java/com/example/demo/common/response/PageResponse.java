package com.example.demo.common.response;

import com.example.demo.common.domain.Pagination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author humbinal
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> extends ListResponse<T> {

    private Integer pageNo;

    private Integer pageSize;

    private Integer totalPage;

    public PageResponse(List<T> list, Long total, Integer pageNo, Integer pageSize, Integer totalPage) {
        super(list, total);
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
    }

    public PageResponse(List<T> list, Pagination pagination) {
        this(list, pagination.getTotal(), pagination.getPageNo(), pagination.getPageSize(), pagination.getTotalPage());
    }
}
