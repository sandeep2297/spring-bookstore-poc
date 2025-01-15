package com.poc.bookstore.repository;

import com.poc.bookstore.entity.BookStoreMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookStoreMappingRepository extends JpaRepository<BookStoreMapping, Integer> {

    List<BookStoreMapping> findByBookIsbn(String isbn);

    List<BookStoreMapping> findByAuthorId(Integer authorId);

    void deleteByBookIsbn(String isbn);

}
