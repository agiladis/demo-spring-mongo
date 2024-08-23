package com.example.demospringmongo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {
    private String id;
    private String title;
    private String author;
    private double price;
}
