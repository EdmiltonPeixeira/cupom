package com.edmilton.cupom.entity;

import com.edmilton.cupom.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;


import java.math.BigDecimal;

@Entity(name = "cupom")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cupom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    @NotBlank
    private String code;
    @NotBlank
    private String description;
    @NotNull
    private BigDecimal discountValue = BigDecimal.valueOf(0.5);
    @NotBlank
    private String expirationDate;
    private Status status;
    private boolean published;
    private boolean redeemed;
}
