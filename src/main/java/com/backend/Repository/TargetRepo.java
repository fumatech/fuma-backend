package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Target;

@Repository
public interface TargetRepo  extends JpaRepository<Target, Long>{

}
