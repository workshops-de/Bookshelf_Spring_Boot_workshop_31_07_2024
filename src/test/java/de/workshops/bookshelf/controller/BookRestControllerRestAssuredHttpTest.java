package de.workshops.bookshelf.controller;

import de.workshops.bookshelf.configuration.TestUser;
import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.repository.BookRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class BookRestControllerRestAssuredHttpTest {

    TestUser testUser;

    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    void setUp(@LocalServerPort int port, @Autowired ApplicationContext context) {
        testUser = TestUser.create(context);

        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        testUser.delete();
    }

    @Test
    void GET_book_returns_all_books() {
        List<Book> books = bookRepository.findAll();

        given().
            log().all().
            auth().basic(testUser.getUsername(), testUser.getPassword()).
        when().
            get("/book").
        then().
            log().all().
            statusCode(200).
            body("author[0]", equalTo(books.get(0).getAuthor()));
    }
}
