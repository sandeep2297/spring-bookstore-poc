package com.poc.bookstore.service;

import com.poc.bookstore.dto.AuthorDTO;
import com.poc.bookstore.dto.BookDTO;
import com.poc.bookstore.entity.Author;
import com.poc.bookstore.entity.Book;
import org.apache.coyote.BadRequestException;

public interface BookStoreService {

    Author addAuthorDetails(Integer userId, AuthorDTO authorDTO);

    Book addBookDetails(Integer userId, BookDTO bookDTO) throws BadRequestException;

}
