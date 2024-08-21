package com.example.demospringmongo.service;

import com.example.demospringmongo.model.Book;
import com.example.demospringmongo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    private final RedisTemplate<String, Book> redisTemplate;
    private final BookRepository bookRepository;

    @Autowired
    public CacheService(RedisTemplate<String, Book> redisTemplate, BookRepository bookRepository) {
        this.redisTemplate = redisTemplate;
        this.bookRepository = bookRepository;
    }

    public Book getBookWithCacheAside(String id) {
        String key = "book:" + id;
        Book book = redisTemplate.opsForValue().get(key);

        if (book == null) {
            book = bookRepository.findById(id).orElse(null);

            if (book != null) {
                redisTemplate.opsForValue().set(key, book, 1, TimeUnit.HOURS);
            }
        }

        return book;
    }

    public void updateBookWithCacheAside(Book book) {
        bookRepository.save(book);
        String key = "book:" + book.getId();
        redisTemplate.opsForValue().set(key, book, 1, TimeUnit.HOURS);
    }
}
