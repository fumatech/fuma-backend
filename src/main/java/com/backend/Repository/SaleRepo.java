package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Sale;
@Repository
public interface SaleRepo extends JpaRepository<Sale, Long> {

}
