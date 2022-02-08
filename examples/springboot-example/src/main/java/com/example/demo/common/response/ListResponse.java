package com.example.demo.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author humbinal
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListResponse<T> {

    private List<T> list;

    private Long total;
}
