package com.example.demo.common.domain;

import com.example.demo.common.utils.Assert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author humbinal
 */
@Getter
public class Sorting {

    private List<String> sortBy;

    private List<String> orders;

    private List<Sort> sorts;

    private Sorting() {
    }

    public static Sorting of(List<String> sortBy, List<String> orders) {
        Assert.isTrue(sortBy != null && orders != null, "sortBy and orders can't be null");
        Sorting sorting = new Sorting();
        List<Sort> sorts = new ArrayList<>(sortBy.size());
        for (int i = 0; i < sortBy.size(); i++) {
            sorts.add(new Sort(sortBy.get(i), Objects.nonNull(orders.get(i)) ? Direction.of(orders.get(i)) : Direction.ASC));
        }
        sorting.setSortBy(sortBy);
        sorting.setOrders(orders);
        sorting.setSorts(sorts);
        return sorting;
    }

    public static Sorting of(List<Sort> sorts) {
        Sorting sorting = new Sorting();
        List<String> sortBy = new ArrayList<>(sorts.size());
        List<String> orders = new ArrayList<>(sorts.size());
        sorts.forEach(sort -> {
            sortBy.add(sort.getField());
            orders.add(Objects.nonNull(sort.getDirection()) ? sort.getDirection().name() : Direction.ASC.name());
        });
        sorting.setSorts(sorts);
        sorting.setSortBy(sortBy);
        sorting.setOrders(orders);
        return sorting;
    }

    private void setSortBy(List<String> sortBy) {
        this.sortBy = sortBy;
    }

    private void setOrders(List<String> orders) {
        this.orders = orders;
    }

    private void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    public enum Direction {

        /**
         * ascending order
         */
        ASC,

        /**
         * descending order
         */
        DESC;

        public static Direction of(String direction) {
            return Direction.valueOf(direction.toUpperCase(Locale.US));
        }
    }

    @Data
    @AllArgsConstructor
    public static class Sort {

        /**
         * sort field
         */
        private String field;

        /**
         * default direction ASC
         */
        private Direction direction;
    }

}
