package com.edmilton.cupom.entity;

import com.edmilton.cupom.dto.CupomResponseDto;
import com.edmilton.cupom.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
    private Instant expirationDate;
    private Status status = Status.ACTIVE;
    @Column(name = "in_published")
    private boolean published = false;
    @Column(name = "in_redeemed")
    private boolean redeemed = false;

    private static final ZoneId BUSINESS_ZONE = ZoneId.of("America/Sao_Paulo");

    @JsonIgnore
    public void sanitizeCode() {
        if(code != null && !code.isBlank()){
            code = code.replaceAll("[^a-zA-Z0-9]", "");
        }
    }

    @JsonIgnore
    public boolean isSanitized() {
        return !code.matches(".*[^a-zA-Z0-9].*");
    }

    @JsonIgnore
    public boolean expirationValid() {
        LocalDate expirationDay = expirationDate
                .atZone(BUSINESS_ZONE)
                .toLocalDate();

        LocalDate today = LocalDate.now(BUSINESS_ZONE);

        return expirationDay.isAfter(today) || expirationDay.equals(today);
    }

    @JsonIgnore
    public boolean isInativo(){
        return status.equals(Status.INACTIVE);
    }

    public CupomResponseDto toResponseDto() {
        CupomResponseDto cupomResponseDto = new CupomResponseDto();
        cupomResponseDto.setId(id);
        cupomResponseDto.setVersion(version);
        cupomResponseDto.setCode(code);
        cupomResponseDto.setDescription(description);
        cupomResponseDto.setDiscountValue(discountValue);
        cupomResponseDto.setExpirationDate(expirationDate.toString());
        cupomResponseDto.setStatus(status);
        cupomResponseDto.setPublished(published);
        cupomResponseDto.setRedeemed(redeemed);

        return cupomResponseDto;
    }
}
