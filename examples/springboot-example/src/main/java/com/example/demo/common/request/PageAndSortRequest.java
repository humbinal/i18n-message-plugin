package com.example.demo.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 参考设计：https://blog.lanweihong.com/posts/62403/
 *
 * @author humbinal
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageAndSortRequest extends PageRequest {

    private List<String> sortBy;

    private List<String> orders;
}
