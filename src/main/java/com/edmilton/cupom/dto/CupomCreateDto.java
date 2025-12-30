package com.edmilton.cupom.dto;

import com.edmilton.cupom.entity.Cupom;
import com.edmilton.cupom.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CupomCreateDto {
    private Long id;
    private Long version;
    @NotBlank
    @JsonProperty("code")
    private String code;
    @NotBlank
    @JsonProperty("description")
    private String description;
    @NotNull
    @JsonProperty("discountValue")
    private BigDecimal discountValue = BigDecimal.valueOf(0.5);
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime expirationDate;
    private Status status;
    private boolean published;
    private boolean redeemed;
}
