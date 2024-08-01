package de.workshops.bookshelf.controller;

import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.domain.BookNotFoundException;
import de.workshops.bookshelf.domain.BookSearchRequest;
import de.workshops.bookshelf.service.BookService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
@Validated
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{isbn}")
    public Book getSingleBookByIsbn(@PathVariable String isbn) {
        return bookService.getSingleBookByIsbn(isbn);
    }

    @GetMapping(params = "author")
    public Book searchBookByAuthor(@RequestParam @NotBlank @Size(min = 3) String author) {
        return bookService.searchBookByAuthor(author);
    }

    @PostMapping("/search")
    public List<Book> searchBooks(@RequestBody BookSearchRequest bookSearchRequest) {
        return bookService.searchBooks(bookSearchRequest);
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(BookNotFoundException bookNotFound) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Book not found: " + bookNotFound.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(ConstraintViolationException violations) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(violations.getMessage());
    }
}
