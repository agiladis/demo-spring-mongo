package com.example.demospringmongo.service;

import com.example.demospringmongo.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
    Optional<Book> getBookById(String id);
    Book saveBook(Book book);
    void deleteBook(String id);
}
