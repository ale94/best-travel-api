package com.alejandro.best_travel.domain.entities;

import com.alejandro.best_travel.util.AeroLine;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "fly")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FlyEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double originLat;
    private Double originLng;
    private Double DestinyLat;
    private Double DestinyLng;

    @Column(length = 20)
    private String originName;

    @Column(length = 20)
    private String DestinyName;
    private BigDecimal price;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private AeroLine aeroLine;

}
