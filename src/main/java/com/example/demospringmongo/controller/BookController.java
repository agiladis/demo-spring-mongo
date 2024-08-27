package com.example.demospringmongo.controller;

import com.example.demospringmongo.exception.EntityNotFoundException;
import com.example.demospringmongo.model.Book;
import com.example.demospringmongo.model.BookDTO;
import com.example.demospringmongo.service.BookService;
import com.example.demospringmongo.utils.BookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        LOG.info("Fetching all books");
        List<BookDTO> books = bookService.getAllBooks().stream()
                .map(BookMapper::toDTO)
                .toList();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable String id) {
        LOG.debug("Fetching book with ID: {}", id);
        return bookService.getBookById(id)
                .map(BookMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        LOG.info("Creating a new book");
        Book savedBook = bookService.saveBook(BookMapper.toEntity(bookDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(BookMapper.toDTO(savedBook));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable String  id, @RequestBody BookDTO bookDTO) {
        LOG.warn("Updating book with ID: {}", id);
        Book updatedBook = bookService.updateBook(id, BookMapper.toEntity(bookDTO));
        return updatedBook != null ? ResponseEntity.ok(BookMapper.toDTO(updatedBook)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        LOG.error("Deleting book with ID: {}", id);
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
