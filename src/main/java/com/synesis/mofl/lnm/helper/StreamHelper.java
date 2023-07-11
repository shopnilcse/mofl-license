package com.synesis.mofl.lnm.helper;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

/**
 * @author S M Abdul Kader
 * @since 07 Mar, 2022
 * @version 1.1
 */
@Component
public class StreamHelper {

    /**
     * This method Filter Distinct NOC Category Id
     *
     * @author S M Abdul Kader
     * @param keyExtractor - function
     * @param <T> - Long
     * @return Distinct Id
     * @since 07 Mar, 2022
     * @version 1.1
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
