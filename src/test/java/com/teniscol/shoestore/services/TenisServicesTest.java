package com.teniscol.shoestore.services;

import com.teniscol.shoestore.identidadesJPA.Tenis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TenisServicesTest {

    @Mock
    TenisRepositoryInterface repository;

    @InjectMocks
    private TenisJPAServices TenisServices;
    private List<Tenis> tenis;

    @BeforeEach
    void setUp() {
        tenis = List.of(
                Tenis.builder()
                        .idTenis(4)
                        .marca("nike")
                        .modelo("air max")
                        .build(),
                Tenis.builder()
                        .idTenis(1)
                         .marca("adidas")
                        .modelo("forum")
                        .build(),
                Tenis.builder()
                        .idTenis(3)
                        .marca("puma")
                        .modelo("speedboots")
                        .build()
        );
    }

    @Test
    void retornaTenisCuandoInvoca_getTenis() {
        when(repository.obtenerTodos()).thenReturn(tenis);
        List<Tenis> result = TenisServices.obtenerTenis();

        assertEquals(4, result.get(0).getIdTenis());
        assertEquals(3, result.get(2).getIdTenis());
        verify(repository).obtenerTodos();
    }

    @Test
    void insertarTenis() {
        Tenis tenis = Tenis.builder()
                .marca("adidas")
                .modelo("campus")
                .precio(450000)
                .build();

        when(repository.insertarTenis(any(Tenis.class))).thenReturn(tenis);
        Tenis result = TenisServices.insertarTenis(tenis);
        assertNotNull(result);
        assertEquals("adidas", result.getMarca());
        verify(repository).insertarTenis(tenis);
    }
}
