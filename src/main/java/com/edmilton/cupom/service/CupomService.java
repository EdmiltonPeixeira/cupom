package com.edmilton.cupom.service;

import com.edmilton.cupom.dto.CupomCreateDto;
import com.edmilton.cupom.entity.Cupom;
import com.edmilton.cupom.enums.Status;
import com.edmilton.cupom.exceptions.InvalidFormatException;
import com.edmilton.cupom.exceptions.EntityInvalidDeleteException;
import com.edmilton.cupom.exceptions.RecursoNaoEncontradoException;
import com.edmilton.cupom.repository.CupomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CupomService {

    private final CupomRepository cupomRepository;

    public CupomService(CupomRepository cupomRepository) {
        this.cupomRepository = cupomRepository;
    }

    @Transactional
    public Cupom create(CupomCreateDto cupomCreateDto) {
        Cupom cupom = cupomCreateDto.toEntity();
        cupom.sanitizeCode();
        if(!cupom.expirationValid()){
            throw new InvalidFormatException("Cupom expirado.");
        }
        if(!cupom.isSanitized()){
            throw new InvalidFormatException("Cupom contém caracteres especiais.");
        }
        cupomRepository.save(cupom);
        return cupom;
    }

    @Transactional
    public void delete(Long id) {
        Cupom cupom = cupomRepository.findById(id).
                orElseThrow(() -> new RecursoNaoEncontradoException("Cupom não existe."));
        if(cupom.isInativo()){
            throw new EntityInvalidDeleteException("Cupom não pode ser excluído novamente.");
        }
        cupom.setStatus(Status.INACTIVE);
        cupomRepository.save(cupom);
    }
}
