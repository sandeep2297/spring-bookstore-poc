package com.poc.bookstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_STORE_MAPPING")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookStoreMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "book_isbn")
    private String bookIsbn;

    @Column(name = "author_id")
    private Integer authorId;

}
