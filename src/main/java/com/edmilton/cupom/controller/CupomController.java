package com.edmilton.cupom.controller;

import com.edmilton.cupom.dto.CupomCreateDto;
import com.edmilton.cupom.entity.Cupom;
import com.edmilton.cupom.service.CupomService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cupom")
public class CupomController {

    private final CupomService cupomService;

    public CupomController(CupomService cupomService) {
        this.cupomService = cupomService;
    }

    @PostMapping
    public ResponseEntity<Cupom> create(@RequestBody @Valid CupomCreateDto cupomCreateDto) {
        Cupom salvo = cupomService.create(cupomCreateDto);
        return ResponseEntity.ok(salvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cupomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
