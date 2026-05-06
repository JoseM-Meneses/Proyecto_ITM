package com.teniscol.shoestore.identidadesJPA;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;
    private String nombre;
    private String correo;
    private String telefono;
}