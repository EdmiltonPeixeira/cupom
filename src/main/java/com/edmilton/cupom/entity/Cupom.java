package com.edmilton.cupom.entity;

import com.edmilton.cupom.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cupom")
public class Cupom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    @NotNull
    private String code;
    @NotNull
    private String description;
    @NotNull
    @Column(name = "discount_value")
    private BigDecimal discountValue = BigDecimal.valueOf(0.5);
    @NotNull
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    private Status status;
    @Column(name = "in_published")
    private boolean published = false;
    @Column(name = "in_redeemed")
    private boolean redeemed = false;

    public void sanitizeCode() {
        if(code != null && !code.isBlank()){
            code = code.replaceAll("[^a-zA-Z0-9]", "");
        }
    }
}
