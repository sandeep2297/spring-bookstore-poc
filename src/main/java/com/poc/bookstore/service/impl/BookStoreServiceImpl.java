package com.poc.bookstore.service.impl;

import com.poc.bookstore.dto.AuthorDTO;
import com.poc.bookstore.dto.AuthorResponseDTO;
import com.poc.bookstore.dto.BookDTO;
import com.poc.bookstore.dto.BookResponseDTO;
import com.poc.bookstore.entity.Author;
import com.poc.bookstore.entity.Book;
import com.poc.bookstore.entity.BookStoreMapping;
import com.poc.bookstore.exception.InvalidAuthorException;
import com.poc.bookstore.exception.NotFoundException;
import com.poc.bookstore.repository.AuthorRepository;
import com.poc.bookstore.repository.BookRepository;
import com.poc.bookstore.repository.BookStoreMappingRepository;
import com.poc.bookstore.service.BookStoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookStoreServiceImpl implements BookStoreService {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private final BookStoreMappingRepository bookStoreMappingRepository;

    @Transactional
    public Author addAuthorDetails(Integer userId, AuthorDTO authorDTO) {
        Optional<Author> authorOptional = authorRepository.findByName(authorDTO.getName());
        if (authorOptional.isPresent()) {
            throw new DuplicateKeyException("Author " + authorDTO.getName() + " exists already");
        } else {
            Date todayDate = new Date();
            Author author = Author.builder()
                    .name(authorDTO.getName())
                    .birthDate(authorDTO.getBirthDate())
                    .createdBy(userId)
                    .createdDate(todayDate)
                    .modifiedBy(userId)
                    .modifiedDate(todayDate).build();
            return authorRepository.save(author);
        }
    }

    @Transactional
    public Book addBookDetails(Integer userId, BookDTO bookDTO) {
        Optional<Book> bookOptional = bookRepository.findByTitle(bookDTO.getTitle());
        if (bookOptional.isPresent()) {
            throw new DuplicateKeyException("Book " + bookDTO.getTitle() + " exists already");
        } else if (Boolean.TRUE.equals(checkIfInvalidAuthorExists(bookDTO.getAuthorIdList()))) {
            throw new InvalidAuthorException("Author List has invalid author");
        } else {
            String uuid = UUID.randomUUID().toString();
            Date todayDate = new Date();
            Book book = Book.builder()
                    .title(bookDTO.getTitle())
                    .publishYear(bookDTO.getPublishYear())
                    .price(bookDTO.getPrice())
                    .genre(bookDTO.getGenre())
                    .isbn(uuid)
                    .createdBy(userId)
                    .createdDate(todayDate)
                    .modifiedBy(userId)
                    .modifiedDate(todayDate).build();

            List<BookStoreMapping> bookStoreMappingList = new ArrayList<>();
            for (Integer authorId : bookDTO.getAuthorIdList()) {
                BookStoreMapping bookStoreMapping = BookStoreMapping.builder()
                        .bookIsbn(uuid)
                        .authorId(authorId).build();
                bookStoreMappingList.add(bookStoreMapping);
            }
            bookStoreMappingRepository.saveAll(bookStoreMappingList);
            return bookRepository.save(book);
        }
    }

    private boolean checkIfInvalidAuthorExists(List<Integer> authorIdListDTO) {
        List<Integer> authorIdList = authorRepository.findAll().stream().map(Author::getId).toList();
        return authorIdListDTO.stream().anyMatch(id -> !authorIdList.contains(id));
    }

    public BookResponseDTO findByBookTitle(String title) {
        Book book = bookRepository.findByTitle(title).orElseThrow(() -> new NotFoundException("Book " + title));
        List<Integer> authorIds = bookStoreMappingRepository.findByBookIsbn(book.getIsbn()).stream()
                .map(BookStoreMapping::getAuthorId).toList();
        if (authorIds.isEmpty()) {
            throw new NotFoundException("Author details for book " + title);
        } else {
            BookResponseDTO bookResponseDTO = new BookResponseDTO();
            List<Author> authorList = new ArrayList<>();
            for (Integer authorId : authorIds) {
                Author author = authorRepository.findById(authorId)
                        .orElseThrow(() -> new NotFoundException("Author details for " + authorId));
                authorList.add(author);
            }
            bookResponseDTO.setBookDetails(book);
            bookResponseDTO.setAuthorDetails(authorList);
            return bookResponseDTO;
        }
    }

    public AuthorResponseDTO findByAuthorName(String authorName) {
        Author author = authorRepository.findByName(authorName).orElseThrow(() -> new NotFoundException("Author " + authorName));
        List<String> bookIsbnList = bookStoreMappingRepository.findByAuthorId(author.getId()).stream()
                .map(BookStoreMapping::getBookIsbn).toList();
        AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO();
        List<Book> bookList = new ArrayList<>();
        if (!bookIsbnList.isEmpty()) {
            for (String isbn : bookIsbnList) {
                Book book = bookRepository.findByIsbn(isbn)
                        .orElseThrow(() -> new NotFoundException("Book details for " + isbn));
                bookList.add(book);
            }
        }
        authorResponseDTO.setAuthorDetails(author);
        authorResponseDTO.setBookDetails(bookList);
        return authorResponseDTO;
    }

}
