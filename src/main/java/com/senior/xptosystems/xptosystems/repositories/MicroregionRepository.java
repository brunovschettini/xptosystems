package com.senior.xptosystems.xptosystems.repositories;

import com.senior.xptosystems.xptosystems.model.Microregion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroregionRepository extends JpaRepository<Microregion, Long> {

    Microregion findByNameIgnoreCase(String name);

}
