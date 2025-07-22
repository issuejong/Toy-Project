package com.group.libraryapp.domain.book;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(nullable = false, length = 255, name = "name")
    String name;

    protected Book() {
    }

    public Book(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
