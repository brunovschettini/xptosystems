package com.senior.xptosystems.repositories;

import com.senior.xptosystems.model.Uf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UfRepository extends JpaRepository<Uf, Long> {

    Uf findByNameIgnoreCase(String name);

}
