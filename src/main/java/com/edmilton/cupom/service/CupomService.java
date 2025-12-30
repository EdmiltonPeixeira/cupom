package com.edmilton.cupom.service;

import com.edmilton.cupom.dto.CupomCreateDto;
import com.edmilton.cupom.entity.Cupom;
import com.edmilton.cupom.exceptions.CupomExpiradoException;
import com.edmilton.cupom.repository.CupomRepository;
import org.springframework.stereotype.Service;

@Service
public class CupomService {

    private final CupomRepository cupomRepository;

    public CupomService(CupomRepository cupomRepository) {
        this.cupomRepository = cupomRepository;
    }

    public Cupom create(CupomCreateDto cupomCreateDto) {
        Cupom cupom = cupomCreateDto.toEntity();
        cupom.sanitizeCode();
        if(cupom.expirationValid()) {
            cupomRepository.save(cupom);
        } else {
            throw new CupomExpiradoException("Cupom expirado.");
        }
        return cupom;
    }
}
