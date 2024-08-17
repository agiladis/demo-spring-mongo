package com.example.demospringmongo.service;

import com.example.demospringmongo.model.Book;
import com.example.demospringmongo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(String id, Book bookDetails) {
        Optional<Book> book = getBookById(id);
        return book.map(b -> {
            b.setTitle(bookDetails.getTitle());
            b.setAuthor(bookDetails.getAuthor());
            b.setPrice(bookDetails.getPrice());
            return bookRepository.save(b);
        }).orElse(null);
    }

    @Override
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }
}
