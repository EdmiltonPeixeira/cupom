package com.edmilton.cupom.controller;

import com.edmilton.cupom.dto.CupomCreateDto;
import com.edmilton.cupom.dto.CupomResponseDto;
import com.edmilton.cupom.entity.Cupom;
import com.edmilton.cupom.service.CupomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/cupom")
@Tag(name = "Cupom", description = "Endpoints para gerenciamento de cupons.")
public class CupomController {

    private final CupomService cupomService;

    public CupomController(CupomService cupomService) {
        this.cupomService = cupomService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar um cupom.",
    description = "Retorna os dados completos do cupom recém criado.",
    responses = {
            @ApiResponse(responseCode = "200", description = "Cupom encontrado",
                    content = @Content(schema = @Schema(implementation = Cupom.class))),
            @ApiResponse(responseCode = "400", description = "Cupom expirado."),
            @ApiResponse(responseCode = "400", description = "Cupom contém caracteres especiais.")
    })
    public ResponseEntity<CupomResponseDto> create(@RequestBody @Valid CupomCreateDto cupomCreateDto) {
        CupomResponseDto response = cupomService.create(cupomCreateDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um cupom de forma lógica.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Cupom excluído com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Cupom não pode ser excluído novamente."),
                    @ApiResponse(responseCode = "404", description = "Cupom não existe.")
            })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cupomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
