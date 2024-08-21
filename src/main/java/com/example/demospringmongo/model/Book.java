package com.example.demospringmongo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Document(collection = "books")
public class Book implements Serializable {
    @Id
    private String id;
    private String title;
    private String author;
    private double price;
}
