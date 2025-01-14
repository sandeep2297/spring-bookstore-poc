package com.poc.bookstore.repository;

import com.poc.bookstore.entity.BookStoreMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStoreMappingRepository extends JpaRepository<BookStoreMapping, Integer> {
}
