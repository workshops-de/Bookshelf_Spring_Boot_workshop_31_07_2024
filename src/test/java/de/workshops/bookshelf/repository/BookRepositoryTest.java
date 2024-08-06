package de.workshops.bookshelf.repository;

import de.workshops.bookshelf.domain.Book;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    @Order(1)
    void getAllBooks_returns_all_books() {
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(3);
    }

    @Test
    @Order(2)
    @Commit
    void saveBook_insert_book() {
        List<Book> books = bookRepository.findAll();

        bookRepository.save(new Book("SQL Explained", "A short guide for SQL", "Bobby Tables", "654456"));

        List<Book> newBooks = bookRepository.findAll();
        assertThat(newBooks).hasSizeGreaterThan(books.size());
    }

    @Test
    @Order(3)
    void getAllBooks_returns_more_books() {
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(4);
    }

    @Test
    void findByIsbnOrAuthor_finds_by_isbn() {
        String isbn = "978-0201633610";
        List<Book> books = bookRepository.findByIsbnOrAuthor(isbn, null);
        assertThat(books).isNotEmpty().allSatisfy(book -> {
            assertThat(book.getIsbn()).isEqualTo(isbn);
        });
    }

    @Test
    void findByIsbnOrAuthor_finds_by_author() {
        String author = "Erich";
        List<Book> books = bookRepository.findByIsbnOrAuthor(null, author);
        assertThat(books).isNotEmpty().allSatisfy(book -> {
            assertThat(book.getAuthor()).contains(author);
        });
    }

    @Test
    void findByIsbnOrAuthor_finds_by_isbn_and_author() {
        String isbn = "978-0201633610";
        String author = "Erich";
        List<Book> books = bookRepository.findByIsbnOrAuthor(null, author);
        assertThat(books).isNotEmpty().allSatisfy(book -> {
            assertThat(book.getIsbn()).isEqualTo(isbn);
            assertThat(book.getAuthor()).contains(author);
        });
    }
}
