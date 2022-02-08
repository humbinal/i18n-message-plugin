package com.example.demo.common.utils;

import com.example.demo.common.domain.Sorting;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections4.comparators.ComparableComparator;
import org.apache.commons.collections4.comparators.ComparatorChain;
import org.apache.commons.collections4.comparators.ReverseComparator;

import java.util.Comparator;
import java.util.List;

/**
 * @author humbinal
 */
public class SortUtils {

    /**
     * Multi Field Bean List Sorter
     *
     * @param list    Multi Field Bean List
     * @param sorting sorting info
     * @param <T>     Multi Field Bean
     */
    public static <T> void sortMultiFieldBean(List<T> list, Sorting sorting) {
        ComparatorChain<T> comparatorChain = new ComparatorChain<>();
        sorting.getSorts().forEach(sort -> {
            @SuppressWarnings("rawtypes") Comparator comparator = ComparableComparator.comparableComparator();
            if (sort.getDirection() == Sorting.Direction.DESC) {
                comparator = new ReverseComparator<>();
            }
            comparatorChain.addComparator(new BeanComparator<>(sort.getField(), comparator));
        });
        list.sort(comparatorChain);
    }
}
