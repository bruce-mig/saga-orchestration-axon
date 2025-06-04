package com.github.bruce_mig.user_service.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "card_details")
public class CardDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "card_number", nullable = false)
    private String cardNumber;
    @Column(name = "valid_until_month", nullable = false)
    private Integer validUntilMonth;
    @Column(name = "valid_until_year", nullable = false)
    private Integer validUntilYear;
    @Column(name = "cvv", nullable = false)
    private Integer cvv;
}
