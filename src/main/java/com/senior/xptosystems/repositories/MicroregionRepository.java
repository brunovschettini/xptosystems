package com.senior.xptosystems.repositories;

import com.senior.xptosystems.model.Microregion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroregionRepository extends JpaRepository<Microregion, Long> {

    Microregion findByNameIgnoreCase(String name);

}
