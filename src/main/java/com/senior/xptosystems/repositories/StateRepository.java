package com.senior.xptosystems.repositories;

import com.senior.xptosystems.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    State findByNameIgnoreCase(String name);

}
