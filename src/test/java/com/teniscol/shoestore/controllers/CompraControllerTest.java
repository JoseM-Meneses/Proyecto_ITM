package com.teniscol.shoestore.controllers;

import com.teniscol.shoestore.DTO.CompraDetalleDTO;
import com.teniscol.shoestore.services.CompraServicesInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompraControllerTest {

    @Mock
    private CompraServicesInterface service;

    @InjectMocks
    private CompraRestController controller;

    private List<CompraDetalleDTO> compras;

    @BeforeEach
    void setUp() {
        CompraDetalleDTO c1 = new CompraDetalleDTO();
        c1.setIdCompra(1);
        c1.setCantidad(2);

        CompraDetalleDTO c2 = new CompraDetalleDTO();
        c2.setIdCompra(2);
        c2.setCantidad(1);

        compras = List.of(c1, c2);
    }

    @Test
    void obtenerCompras_retornaLista() {
        when(service.obtenerCompras()).thenReturn(compras);

        ResponseEntity<List<CompraDetalleDTO>> response = controller.obtenerCompras();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        verify(service).obtenerCompras();
    }

    @Test
    void comprarTenis_exitosa() {
        when(service.realizarCompra(1,1,1,40,2)).thenReturn(true);

        ResponseEntity<String> response = controller.comprarTenis(1,1,1,40,2);

        assertEquals(201, response.getStatusCode().value());
        assertTrue(response.getBody().contains("éxito"));
        verify(service).realizarCompra(1,1,1,40,2);
    }

}
