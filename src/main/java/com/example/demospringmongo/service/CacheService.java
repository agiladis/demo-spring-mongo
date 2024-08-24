package com.example.demospringmongo.service;

import com.example.demospringmongo.model.Book;
import com.example.demospringmongo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CacheService {

    private final CacheManager cacheManager;
    private final BookRepository bookRepository;

    @Autowired
    public CacheService(CacheManager cacheManager, BookRepository bookRepository) {
        this.cacheManager = cacheManager;
        this.bookRepository = bookRepository;
    }

    public void updateAllBooksCache(Book savedBook) {
        Cache cache = cacheManager.getCache("books");
        if (cache != null) {
            List<Book> allBooks = cache.get("all", List.class);
            if (allBooks != null) {
                List<Book> updatedBooks = allBooks.stream()
                        .map(book -> book.getId().equals(savedBook.getId()) ? savedBook : book)
                        .collect(Collectors.toList());

                if (updatedBooks.stream().noneMatch(book -> book.getId().equals(savedBook.getId()))) {
                    updatedBooks.add(savedBook);
                }

                cache.put("all", updatedBooks);
            } else {
                allBooks = List.of(savedBook);
                cache.put("all", allBooks);
            }
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
