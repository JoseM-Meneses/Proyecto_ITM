package com.teniscol.shoestore.services;

import com.teniscol.shoestore.DTO.CompraDetalleDTO;
import com.teniscol.shoestore.repositoriesSQL.CompraRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompraServicesTest {

    @Mock
    private CompraRepositoryInterface repository;

    @InjectMocks
    private CompraServices service;

    private List<CompraDetalleDTO> compras;

    @BeforeEach
    void setUp() {
        CompraDetalleDTO c1 = new CompraDetalleDTO();
        c1.setIdCompra(1);
        c1.setIdCliente(10);
        c1.setCantidad(2);

        CompraDetalleDTO c2 = new CompraDetalleDTO();
        c2.setIdCompra(2);
        c2.setIdCliente(20);
        c2.setCantidad(1);

        compras = List.of(c1, c2);
    }

    @Test
    void retornaComprasCuandoInvoca_obtenerCompras() {
        when(repository.obtenerCompras()).thenReturn(compras);

        List<CompraDetalleDTO> result = service.obtenerCompras();

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getIdCompra());
        verify(repository).obtenerCompras();
    }

    @Test
    void realizarCompraExitosa() {
        when(repository.realizarCompra(anyInt(), anyInt(), anyInt(), anyInt(), anyInt()))
                .thenReturn(true);

        boolean result = service.realizarCompra(1, 1, 1, 40, 2);

        assertTrue(result);
        verify(repository).realizarCompra(1, 1, 1, 40, 2);
    }

    @Test
    void eliminarCompra() {
        when(repository.eliminarCompra(1)).thenReturn(true);

        boolean result = service.eliminarCompra(1);

        assertTrue(result);
        verify(repository).eliminarCompra(1);
    }
}
