package com.example.demo.common.request;

import lombok.Data;

import java.util.List;

/**
 * 参考设计：https://blog.lanweihong.com/posts/62403/
 *
 * @author humbinal
 */
@Data
public class SortRequest {

    private List<String> sortBy;

    private List<String> orders;
}
