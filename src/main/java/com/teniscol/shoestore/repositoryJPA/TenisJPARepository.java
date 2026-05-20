package com.teniscol.shoestore.repositoryJPA;

import com.teniscol.shoestore.identidadesJPA.Tenis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenisJPARepository extends JpaRepository<Tenis, Integer> {

}
