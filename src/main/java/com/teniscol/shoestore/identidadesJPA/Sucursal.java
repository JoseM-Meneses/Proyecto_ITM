package com.teniscol.shoestore.identidadesJPA;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sucursal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal")
    private Integer idSucursal;
    private String nombre;
    private String ciudad;
}
