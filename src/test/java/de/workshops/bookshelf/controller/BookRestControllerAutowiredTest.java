package de.workshops.bookshelf.controller;

import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static de.workshops.bookshelf.domain.BookTestData.knownBooks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookRestControllerAutowiredTest {

    @Autowired
    BookRestController bookRestController;

    @SpyBean
    BookService bookService;

    @Test
    void getAllBooks_should_return_all_books() {
        // Arrange
        when(bookService.getAllBooks()).thenReturn(knownBooks);

        // Act
        List<Book> books = bookRestController.getAllBooks();

        // Assert
        assertThat(books)
                .hasSameSizeAs(knownBooks)
                .isEqualTo(knownBooks);
    }
}
