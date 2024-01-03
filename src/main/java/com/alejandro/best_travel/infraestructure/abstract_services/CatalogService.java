package com.alejandro.best_travel.infraestructure.abstract_services;

import com.alejandro.best_travel.util.SortType;
import java.math.BigDecimal;
import java.util.Set;
import org.springframework.data.domain.Page;

public interface CatalogService<R> {

    Page<R> realAll(Integer page, Integer size, SortType sortType);

    Set<R> readLessPrice(BigDecimal price);

    Set<R> readBetweenPrice(BigDecimal min, BigDecimal max);

    String FIELD_BY_SORT = "price";

}
