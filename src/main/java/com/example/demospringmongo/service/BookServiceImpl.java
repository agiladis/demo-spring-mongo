package com.example.demospringmongo.service;

import com.example.demospringmongo.exception.EntityNotFoundException;
import com.example.demospringmongo.model.Book;
import com.example.demospringmongo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "books", key = "'all'")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Cacheable(value = "books", key = "#id")
    public Optional<Book> getBookById(String id) {
        return Optional.ofNullable(bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("book not found with id: " + id)));
    }

    @Override
    @CachePut(value = "books", key = "#book.id")
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @CachePut(value = "books", key = "#id")
    public Book updateBook(String id, Book bookDetails) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    updateBookDetails(existingBook, bookDetails);
                    return bookRepository.save(existingBook);
                }).orElseThrow(() -> new EntityNotFoundException("book not found with id: " + id));
    }

    @Override
    @CacheEvict(value = "books", key = "#id")
    public void deleteBook(String id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    private void updateBookDetails(Book book, Book bookDetails) {
        if (bookDetails.getTitle() != null) {
            book.setTitle(bookDetails.getTitle());
        }
        if (bookDetails.getAuthor() != null) {
            book.setAuthor(bookDetails.getAuthor());
        }
        book.setPrice(bookDetails.getPrice());
    }
}
