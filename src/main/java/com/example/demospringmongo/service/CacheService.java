package com.example.demospringmongo.service;

import com.example.demospringmongo.model.Book;
import com.example.demospringmongo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheService {

    private final CacheManager cacheManager;
    private final BookRepository bookRepository;

    @Autowired
    public CacheService(CacheManager cacheManager, BookRepository bookRepository) {
        this.cacheManager = cacheManager;
        this.bookRepository = bookRepository;
    }

    public void updateAllBooksCache() {
        List<Book> allBooks = bookRepository.findAll();
        Cache cache = cacheManager.getCache("books");
        if (cache != null) {
            cache.put("all", allBooks);
        }
    }
    public void updateBookCache(Book book) {
        Cache cache = cacheManager.getCache("books");
        if (cache != null) {
            cache.put(book.getId(), book);
        } else {

        }
    }

    public void deleteBookCache(String id) {
        Cache cache = cacheManager.getCache("books");
        if (cache != null) {
            cache.evict(id);
        } else {

        }
    }
}
