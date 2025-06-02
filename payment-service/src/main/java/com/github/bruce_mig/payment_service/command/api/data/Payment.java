package com.github.bruce_mig.payment_service.command.api.data;

import com.github.bruce_mig.commons.utils.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String paymentId;
    private String orderId;
    private LocalDateTime time;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
