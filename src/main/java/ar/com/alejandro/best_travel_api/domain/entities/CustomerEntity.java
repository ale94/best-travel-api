package ar.com.alejandro.best_travel_api.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerEntity implements Serializable {

    @Id
    @Column(length = 20)
    private String dni;
    @Column(length = 50)
    private String fullName;
    @Column(length = 20)
    private String creditCard;
    @Column(length = 20)
    private String phoneNumber;
    private Integer totalFlights;
    private Integer totalLodgings;
    private Integer totalTours;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "customer"
    )
    private List<TourEntity> tours;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "customer"
    )
    private List<ReservationEntity> reservations;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "customer"
    )
    private List<TicketEntity> tickets;

}