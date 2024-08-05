package de.workshops.bookshelf.service;

import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.domain.BookNotFoundException;
import de.workshops.bookshelf.domain.BookSearchRequest;
import de.workshops.bookshelf.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public Book getSingleBookByIsbn(String isbn) {
        return bookRepository.getAllBooks().stream()
                .filter(book -> hasIsbn(book, isbn))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("ISBN " + isbn));
    }

    public Book searchBookByAuthor(String author) {
        return bookRepository.getAllBooks().stream()
                .filter(book -> hasAuthor(book, author))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("author = '" + author + "'"));
    }

    public List<Book> searchBooks(BookSearchRequest bookSearchRequest) {
        return bookRepository.getAllBooks().stream()
                .filter(book -> bookSearchRequest.getIsbn() == null || hasIsbn(book, bookSearchRequest.getIsbn()))
                .filter(book -> bookSearchRequest.getAuthor() == null || hasAuthor(book, bookSearchRequest.getAuthor()))
                .toList();
    }

    public void createBook(Book book) {
        bookRepository.saveBook(book);
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }
}
