package com.teniscol.shoestore.repositoryJPA;

import com.teniscol.shoestore.identidadesJPA.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteJPARepository extends JpaRepository<Cliente, Integer> {
}
