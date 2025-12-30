package com.edmilton.cupom.dto;

import com.edmilton.cupom.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CupomResponseDto {
    @Schema(description = "ID do cupom", example = "1")
    private Long id;
    @Schema(description = "Versão do objeto", example = "0")
    private Long version;
    @Schema(description = "Código do cupom", example = "edm50")
    private String code;
    @Schema(description = "Descrição do cupom", example = "Concede 50% de desconto")
    private String description;
    @Schema(description = "Percentual de desconto do cupom", example = "0.5")
    private BigDecimal discountValue;
    @Schema(description = "Data de expiração do cupom", example = "2025-12-31T18:47:32.123Z")
    private String expirationDate;
    @Schema(description = "Status do cupom", example = "ACTIVE")
    private Status status;
    @Schema(description = "Controle de publicação do cupom", example = "true")
    private boolean published;
    @Schema(description = "Controle de resgate do cupom", example = "false")
    private boolean redeemed;
}
