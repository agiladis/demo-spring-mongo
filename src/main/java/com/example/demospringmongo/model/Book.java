package com.example.demospringmongo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@Setter
@Document(collection = "books")
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private double price;
}
