package ar.com.alejandro.best_travel_api.infraestructure.services;

import ar.com.alejandro.best_travel_api.api.models.responses.FlyResponse;
import ar.com.alejandro.best_travel_api.domain.entities.FlyEntity;
import ar.com.alejandro.best_travel_api.domain.repositories.FlyRepository;
import ar.com.alejandro.best_travel_api.infraestructure.abstract_services.IFlyService;
import ar.com.alejandro.best_travel_api.util.constants.CacheConstants;
import ar.com.alejandro.best_travel_api.util.enums.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class FlyService implements IFlyService {

    private final FlyRepository flyRepository;

    @Override
    public Page<FlyResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;
        switch (sortType) {
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }
        return this.flyRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Cacheable(value = CacheConstants.FLY_CACHE_NAME)
    @Override
    public Set<FlyResponse> readLessPrice(BigDecimal price) {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this.flyRepository.selectLessPrice(price)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());

    }

    @Cacheable(value = CacheConstants.FLY_CACHE_NAME)
    @Override
    public Set<FlyResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this.flyRepository.selectBetweenPrice(min, max)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Cacheable(value = CacheConstants.FLY_CACHE_NAME)
    @Override
    public Set<FlyResponse> readByOriginDestiny(String origin, String destiny) {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this.flyRepository.selectOriginDestiny(origin, destiny)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    private FlyResponse entityToResponse(FlyEntity entity) {
        var response = new FlyResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}
