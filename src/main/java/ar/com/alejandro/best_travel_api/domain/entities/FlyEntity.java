package ar.com.alejandro.best_travel_api.domain.entities;

import ar.com.alejandro.best_travel_api.util.AeroLine;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity(name = "fly")
public class FlyEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double originLat;
    private Double originLng;
    private Double destinyLat;
    private Double destinyLng;
    @Column(length = 20)
    private String originName;
    @Column(length = 20)
    private String destinyName;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private AeroLine aeroLine;

}
