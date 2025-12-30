package com.edmilton.cupom.service;

import com.edmilton.cupom.dto.CupomCreateDto;
import com.edmilton.cupom.dto.CupomResponseDto;
import com.edmilton.cupom.entity.Cupom;
import com.edmilton.cupom.enums.Status;
import com.edmilton.cupom.exceptions.EntityInvalidDeleteException;
import com.edmilton.cupom.exceptions.InvalidFormatException;
import com.edmilton.cupom.exceptions.RecursoNaoEncontradoException;
import com.edmilton.cupom.repository.CupomRepository;
import jdk.jfr.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CupomServiceTest {
    @Mock
    private CupomRepository repository;
    @InjectMocks
    private CupomService service;
    private CupomCreateDto cupomCreateDto;
    private Cupom cupom;
    Cupom spyCupom;
    CupomCreateDto spyCupomCreateDto;

    @BeforeEach
    void setUp() {
        cupom = new Cupom();
        cupom.setId(1L);
        cupom.setCode("EDM@50");
        cupom.setDescription("Cupom de desconto de 50%.");
        cupom.setExpirationDate(Instant.parse("2025-12-30T13:26:00Z"));
        cupom.setStatus(Status.ACTIVE);

        cupomCreateDto = new CupomCreateDto();
        cupomCreateDto.setCode("EDM@50");
        cupomCreateDto.setDescription("Cupom de desconto de 50%.");
        cupomCreateDto.setExpirationDate("2025-12-30T13:26:00Z");
        cupomCreateDto.setStatus(Status.ACTIVE);

        spyCupom = Mockito.spy(cupom);
        spyCupomCreateDto = Mockito.spy(cupomCreateDto);
    }

    @Test
    void deveCadastrarCupomSemCaracteresEspeciais() {
        CupomResponseDto result = service.create(cupomCreateDto);
        assertEquals("EDM50", result.getCode());
    }

    @Test
    void naoDeveCadastrarCupomComCaracteresEspeciais() {
        doReturn(spyCupom).when(spyCupomCreateDto).toEntity();
        doAnswer(invocation -> null).when(spyCupom).sanitizeCode();
        InvalidFormatException exception =
                assertThrows(InvalidFormatException.class, () -> {
                    service.create(spyCupomCreateDto);
                });
        assertTrue(exception.getMessage().contains("Cupom contém caracteres especiais."));
    }

    @Test
    void deveCadastrarCupomComExpiracaoValida(){
        when(cupomCreateDto.toEntity()).thenReturn(cupom);
        CupomResponseDto result = service.create(cupomCreateDto);
        assertTrue(cupom.expirationValid());
        assertEquals(Status.ACTIVE, result.getStatus());
    }

    @Test
    void naoDeveCadastrarCupomComExpiracaoInvalida(){
        cupom.setExpirationDate(Instant.parse("2025-12-29T13:26:00Z"));
        cupomCreateDto.setExpirationDate("2025-12-29T13:26:00Z");
        Cupom spy = Mockito.spy(cupom);

        doReturn(spy).when(spyCupomCreateDto).toEntity();
        InvalidFormatException exception =
                assertThrows(InvalidFormatException.class, () -> {
                    service.create(spyCupomCreateDto);
                });
        assertTrue(exception.getMessage().contains("Cupom expirado."));
    }

    @Test
    void deveLancarExcecaoAoBuscarCupomInexistenteParaExcluir(){
        when(repository.findById(anyLong())).thenThrow(new RecursoNaoEncontradoException("Cupom não existe."));

        RecursoNaoEncontradoException exception =
                assertThrows(RecursoNaoEncontradoException.class, () -> {
                    service.delete(1L);
                });
        assertTrue(exception.getMessage().contains("Cupom não existe."));
    }

    @Test
    void deveLancarExcecaoAoTentarExcluirCupomJaExcluido(){
        cupom.setStatus(Status.INACTIVE);
        Long id = cupom.getId();

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(cupom));

        EntityInvalidDeleteException exception =
                assertThrows(EntityInvalidDeleteException.class, () -> {
                    service.delete(id);
                });
        assertTrue(exception.getMessage().contains("Cupom não pode ser excluído novamente."));
    }

    @Test
    void deveExcluirCupomComSucesso(){
        Long id = cupom.getId();
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(cupom));

        service.delete(id);
        assertTrue(Status.INACTIVE.equals(cupom.getStatus()));
    }
}
