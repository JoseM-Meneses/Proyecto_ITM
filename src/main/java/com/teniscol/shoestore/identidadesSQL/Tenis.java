package com.teniscol.shoestore.identidadesSQL;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tenis {
    private Integer idTenis;
    private String marca;
    private String modelo;
    private double precio;
    private int stock;
}
