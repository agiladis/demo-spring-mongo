package com.example.demospringmongo.service;

import com.example.demospringmongo.exception.EntityNotFoundException;
import com.example.demospringmongo.model.Book;
import com.example.demospringmongo.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

//    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
    private final BookRepository bookRepository;
    private final CacheService cacheService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, CacheService cacheService) {
        this.bookRepository = bookRepository;
        this.cacheService = cacheService;
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
//        logger.info("Saving a new book with title: {}", book.getTitle());

        Book savedBook = bookRepository.save(book);
        cacheService.updateAllBooksCache(savedBook);
        return savedBook;
    }

    @Override
    @CachePut(value = "books", key = "#id")
    public Book updateBook(String id, Book bookDetails) {
        return bookRepository.findById(id)
                .map(book -> {
                    updateBookDetails(book, bookDetails);
                    Book updatedBook = bookRepository.save(book);
                    cacheService.updateAllBooksCache(updatedBook);
                    return updatedBook;
                }).orElseThrow(() -> new EntityNotFoundException("book not found with id: " + id));
    }

    @Override
    @CacheEvict(value = "books", key = "#id")
    public void deleteBook(String id) {
//        logger.debug("Attempting to delete book with id: {}", id);

        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("book not found with id: " + id);
        }
        bookRepository.deleteById(id);
        cacheService.deleteBookCacheFromList(id);
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
