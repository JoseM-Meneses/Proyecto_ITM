package com.teniscol.shoestore.identidadesJPA;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tenis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tenis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tenis")
    private Integer idTenis;
    private String marca;
    private String modelo;
    private double precio;
    private int stock;
}
