package com.example.demospringmongo.service;

import com.example.demospringmongo.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CacheService {

    private final CacheManager cacheManager;

    @Autowired
    public CacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
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
            }
        }
    }

    public void deleteBookCacheFromList(String id) {
        Cache cache = cacheManager.getCache("books");
        if (cache != null) {
            List<Book> allBooks = cache.get("all", List.class);
            if (allBooks != null) {
                List<Book> updatedBooks = allBooks.stream()
                        .filter(book -> !book.getId().equals(id))
                        .collect(Collectors.toList());

                cache.put("all", updatedBooks);
            }
        }
    }
}
