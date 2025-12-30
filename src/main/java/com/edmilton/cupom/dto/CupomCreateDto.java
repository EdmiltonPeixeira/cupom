package com.edmilton.cupom.dto;

import com.edmilton.cupom.entity.Cupom;
import com.edmilton.cupom.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String code;
    @NotBlank
    private String description;
    @NotNull
    private BigDecimal discountValue = BigDecimal.valueOf(0.5);
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime expirationDate;
    private Status status;
    private boolean published;
    private boolean redeemed;

    public Cupom toEntity() {
        Cupom cupom = new Cupom();
        cupom.setId(this.id);
        cupom.setVersion(this.version);
        cupom.setCode(this.code);
        cupom.setDescription(this.description);
        cupom.setDiscountValue(this.discountValue);
        cupom.setExpirationDate(this.expirationDate);
        cupom.setStatus(this.status);
        cupom.setPublished(this.published);
        cupom.setRedeemed(this.redeemed);
        return cupom;
    }
}
