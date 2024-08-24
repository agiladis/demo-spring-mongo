package com.example.demospringmongo.utils;

import com.example.demospringmongo.model.Book;
import com.example.demospringmongo.model.BookDTO;

public class BookMapper {

    private BookMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPrice(book.getPrice());
        return dto;
    }

    public static Book toEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPrice(dto.getPrice());
        return book;
    }
}
