package com.poc.bookstore.controller;

import com.poc.bookstore.dto.AuthorDTO;
import com.poc.bookstore.dto.BookDTO;
import com.poc.bookstore.dto.Response;
import com.poc.bookstore.service.BookStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class BookStoreController {

    private final BookStoreService bookStoreService;

    /**
     * API Endpoint to save new author details
     * Saves and Returns the saved author details.
     */
    @PostMapping("/author-details")
    public ResponseEntity<Response> addAuthorDetails(@RequestHeader("userId") Integer userId, @RequestBody AuthorDTO authorDTO) {
        return new ResponseEntity<>(new Response(HttpStatus.OK.value(), bookStoreService.addAuthorDetails(userId, authorDTO)), HttpStatus.OK);
    }

    /**
     * API Endpoint to save new book details
     * Saves and Returns the saved book details.
     */
    @PostMapping("/book-details")
    public ResponseEntity<Response> addBookDetails(@RequestHeader("userId") Integer userId, @RequestBody BookDTO bookDTO) {
        return new ResponseEntity<>(new Response(HttpStatus.OK.value(), bookStoreService.addBookDetails(userId, bookDTO)), HttpStatus.OK);
    }

    /**
     * API Endpoint to fetch book details
     * Returns list of books along with respective author details
     */
    @GetMapping("/book-details")
    public ResponseEntity<Response> getBookDetails(@RequestParam(value = "bookTitle", required = true) String bookTitle) {
        return new ResponseEntity<>(new Response(HttpStatus.OK.value(), bookStoreService.findByBookTitle(bookTitle)), HttpStatus.OK);
    }

    /**
     * API Endpoint to fetch author details
     * Returns list of author with respective book details
     */
    @GetMapping("/author-details")
    public ResponseEntity<Response> getAuthorDetails(@RequestParam(value = "authorName", required = true) String authorName) {
        return new ResponseEntity<>(new Response(HttpStatus.OK.value(), bookStoreService.findByAuthorName(authorName)), HttpStatus.OK);
    }

}
