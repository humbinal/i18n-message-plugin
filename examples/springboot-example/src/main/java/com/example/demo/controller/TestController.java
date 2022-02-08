package com.example.demo.controller;

import com.example.demo.common.domain.Pagination;
import com.example.demo.common.domain.Sorting;
import com.example.demo.common.exception.CommonException;
import com.example.demo.common.request.PageAndSortRequest;
import com.example.demo.common.request.PageRequest;
import com.example.demo.common.request.SortRequest;
import com.example.demo.common.response.CommonResponse;
import com.example.demo.common.response.ListResponse;
import com.example.demo.common.response.PageResponse;
import com.example.demo.common.utils.ResponseUtils;
import com.example.demo.common.utils.SortUtils;
import com.example.demo.entity.User;
import com.example.demo.i18n.TestError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author humbinal
 */
@Slf4j
@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("/test/hello")
    public CommonResponse<String> testHello() {
        return ResponseUtils.success("Hello,World");
    }

    @GetMapping("/test/user")
    public CommonResponse<User> testUser() {
        User user = new User("tom", 18);
        return ResponseUtils.success(user);
    }

    @GetMapping("/test/exp")
    public CommonResponse<String> testExp() throws CommonException {
        throw new CommonException("user not exist", TestError.USER_NOT_EXIST);
    }

    @GetMapping("/test/exp2")
    public CommonResponse<String> testExp2() throws Throwable {
        throw new Throwable("exp2");
    }

    /**
     * for normal test:
     * curl -i -X POST \
     * -H "Content-Type:application/json" \
     * -d \
     * '{"name":"tom","age":20}' \
     * 'http://localhost:8080/test/post'
     * <p>
     * for validation test:
     * curl -i -X POST \
     * -H "Content-Type:application/json" \
     * -d \
     * '{"name":" ","age":300}' \
     * 'http://localhost:8080/test/post'
     */
    @PostMapping("/test/post")
    public CommonResponse<User> testPost(@Valid @RequestBody User user) {
        return ResponseUtils.success(user);
    }

    /**
     * for normal test:
     * curl -i -X GET \
     * 'http://localhost:8080/valid/page?pageNo=1&pageSize=10'
     * <p>
     * for validation test:
     * curl -i -X GET \
     * 'http://localhost:8080/valid/page?pageNo=-1&pageSize=-10'
     */
    @GetMapping("/test/page-by-get")
    public CommonResponse<PageResponse<User>> testPageByGet(@Valid @ModelAttribute PageRequest request) {
        Pagination pagination = Pagination.paging(request.getPageNo(), request.getPageSize());
        List<User> users = new ArrayList<>();
        users.add(new User("tom", 11));
        users.add(new User("jerry", 10));
        pagination.setTotalPage(1);
        pagination.setTotal(2L);
        return ResponseUtils.page(users, pagination);
    }

    /**
     * for normal test:
     * curl -i -X POST \
     * -H "Content-Type:application/json" \
     * -d \
     * '{"pageNo":1,"pageSize":10}' \
     * 'http://localhost:8080/valid/page'
     * <p>
     * for validation test:
     * curl -i -X POST \
     * -H "Content-Type:application/json" \
     * -d \
     * '{"pageNo":-1,"pageSize":-10}' \
     * 'http://localhost:8080/valid/page'
     */
    @PostMapping("/test/page-by-post")
    public CommonResponse<PageResponse<User>> testPageByPost(@Valid @RequestBody PageRequest request) {
        Pagination pagination = Pagination.paging(request.getPageNo(), request.getPageSize());
        List<User> users = new ArrayList<>();
        users.add(new User("tom", 12));
        users.add(new User("jerry", 10));
        pagination.setTotalPage(1);
        pagination.setTotal(2L);
        return ResponseUtils.page(users, pagination);
    }


    /**
     * for sort:
     * curl -i -X GET \
     * 'http://localhost:8080/test/sort-by-get?sortBy=name%2Cage&orders=asc%2Cdesc'
     */
    @GetMapping("/test/sort-by-get")
    public CommonResponse<ListResponse<User>> testSortByGet(@Valid @ModelAttribute SortRequest sortRequest) {
        log.info("sortRequest: {}", sortRequest);
        List<String> sortBy = sortRequest.getSortBy();
        List<String> orders = sortRequest.getOrders();
        List<User> users = new ArrayList<>();
        users.add(new User("tom", 13));
        users.add(new User("tom", 11));
        users.add(new User("tom", 12));
        users.add(new User("jerry", 10));
        users.add(new User("bob", 10));
        SortUtils.sortMultiFieldBean(users, Sorting.of(sortBy, orders));
        return ResponseUtils.list(users);
    }

    /**
     * for sort:
     * curl -i -X POST \
     * -H "Content-Type:application/json" \
     * -d \
     * '{"sortBy":["name","age"],"orders":["asc","desc"]}' \
     * 'http://localhost:8080/test/sort-by-post'
     */
    @PostMapping("/test/sort-by-post")
    public CommonResponse<ListResponse<User>> testSortByPost(@Valid @RequestBody SortRequest sortRequest) {
        log.info("sortRequest: {}", sortRequest);
        List<String> sortBy = sortRequest.getSortBy();
        List<String> orders = sortRequest.getOrders();
        List<User> users = new ArrayList<>();
        users.add(new User("tom", 13));
        users.add(new User("tom", 11));
        users.add(new User("tom", 12));
        users.add(new User("jerry", 10));
        users.add(new User("bob", 10));
        SortUtils.sortMultiFieldBean(users, Sorting.of(sortBy, orders));
        return ResponseUtils.list(users);
    }

    /**
     * for normal test:
     * curl -i -X POST \
     * -H "Content-Type:application/json" \
     * -d \
     * '{"pageNo":1,"pageSize":10,"sortBy":["name","age"],"orders":["asc","desc"]}' \
     * 'http://localhost:8080/test/page-and-sort'
     */
    @PostMapping("/test/page-and-sort")
    public CommonResponse<PageResponse<User>> testPageAndSort(@Valid @RequestBody PageAndSortRequest pageAndSortRequest) {
        Pagination pagination = Pagination.paging(pageAndSortRequest.getPageNo(), pageAndSortRequest.getPageSize());
        List<String> sortBy = pageAndSortRequest.getSortBy();
        List<String> orders = pageAndSortRequest.getOrders();
        log.info("pageAndSortRequest: {}", pageAndSortRequest);
        List<User> users = new ArrayList<>();
        users.add(new User("tom", 13));
        users.add(new User("tom", 11));
        users.add(new User("tom", 12));
        users.add(new User("jerry", 10));
        users.add(new User("bob", 10));
        SortUtils.sortMultiFieldBean(users, Sorting.of(sortBy, orders));
        pagination.setTotalPage(1);
        pagination.setTotal(5L);
        return ResponseUtils.page(users, pagination);
    }

}
