package com.poc.bookstore.dto;

import com.poc.bookstore.entity.Author;
import com.poc.bookstore.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponseDTO {

    private Author authorDetails;

    private List<Book> bookDetails;

}
