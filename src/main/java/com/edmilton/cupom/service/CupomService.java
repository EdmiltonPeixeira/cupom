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
        Cupom cupom = toEntity(cupomCreateDto);
        cupom.sanitizeCode();
        if(cupom.expirationValid()) {
            cupomRepository.save(cupom);
        } else {
            throw new CupomExpiradoException("Cupom expirado.");
        }
        return cupom;
    }

    private Cupom toEntity(CupomCreateDto cupomCreateDto) {
        Cupom cupom = new Cupom();
        cupom.setCode(cupomCreateDto.getCode());
        cupom.setDescription(cupomCreateDto.getDescription());
        cupom.setDiscountValue(cupomCreateDto.getDiscountValue());
        cupom.setExpirationDate(cupomCreateDto.getExpirationDate());
        cupom.setStatus(cupomCreateDto.getStatus());
        cupom.setPublished(cupomCreateDto.isPublished());
        cupom.setRedeemed(cupomCreateDto.isRedeemed());
        return cupom;
    }
}
