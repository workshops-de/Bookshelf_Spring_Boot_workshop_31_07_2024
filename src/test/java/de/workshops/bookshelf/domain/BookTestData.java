package de.workshops.bookshelf.domain;

import java.util.List;

public class BookTestData {

    public static List<Book> knownBooks = List.of(
            new Book("Spring Boot Explained", "A book about Spring Boot", "Someone", "123456"),
            new Book("Awesome Tests", "Testing Essentials", "Someone else", "987654"),
            new Book("Know Your IDE", "About the importance of an IDE", "Someone", "123321")
    );
}
