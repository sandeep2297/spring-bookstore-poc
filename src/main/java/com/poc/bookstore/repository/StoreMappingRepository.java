package com.poc.bookstore.repository;

import com.poc.bookstore.entity.Store_Mapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreMappingRepository extends JpaRepository<Store_Mapping, Integer> {
}
