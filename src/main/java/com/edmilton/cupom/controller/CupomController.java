package com.edmilton.cupom.controller;

import com.edmilton.cupom.dto.CupomCreateDto;
import com.edmilton.cupom.entity.Cupom;
import com.edmilton.cupom.service.CupomService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cupom")
public class CupomController {

    private final CupomService cupomService;

    public CupomController(CupomService cupomService) {
        this.cupomService = cupomService;
    }

    @PostMapping
    public Cupom create(@RequestBody @Valid CupomCreateDto cupomCreateDto) {
        return cupomService.create(cupomCreateDto);
    }
}
