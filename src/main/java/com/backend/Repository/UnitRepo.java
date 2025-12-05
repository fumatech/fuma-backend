package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Units;
@Repository
public interface UnitRepo extends JpaRepository<Units,Long> {

}
