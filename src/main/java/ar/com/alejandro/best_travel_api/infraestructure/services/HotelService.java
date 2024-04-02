package ar.com.alejandro.best_travel_api.infraestructure.services;

import ar.com.alejandro.best_travel_api.api.models.responses.HotelResponse;
import ar.com.alejandro.best_travel_api.domain.entities.HotelEntity;
import ar.com.alejandro.best_travel_api.domain.repositories.HotelRepository;
import ar.com.alejandro.best_travel_api.infraestructure.abstract_services.IHotelService;
import ar.com.alejandro.best_travel_api.util.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class HotelService implements IHotelService {

    private final HotelRepository hotelRepository;

    @Override
    public Page<HotelResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;
        switch (sortType) {
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }
        return this.hotelRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Override
    public List<HotelResponse> readLessPrice(BigDecimal price) {
        return this.hotelRepository.findByPriceLessThan(price)
                .stream()
                .map(this::entityToResponse)
                .toList();
    }

    @Override
    public List<HotelResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
        return this.hotelRepository.findByPriceBetween(min, max)
                .stream()
                .map(this::entityToResponse)
                .toList();
    }

    @Override
    public List<HotelResponse> readByRating(Integer rating) {
        return this.hotelRepository.findByRatingGreaterThan(rating)
                .stream()
                .map(this::entityToResponse)
                .toList();
    }

    private HotelResponse entityToResponse(HotelEntity entity) {
        var response = new HotelResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}
