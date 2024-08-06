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
        return bookRepository.findAll();
    }

    public Book getSingleBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("ISBN " + isbn));
    }

    public Book searchBookByAuthor(String author) {
        return bookRepository.findByAuthor(author)
                .orElseThrow(() -> new BookNotFoundException("author = '" + author + "'"));
    }

    public List<Book> searchBooks(BookSearchRequest bookSearchRequest) {
        return bookRepository.findByIsbnOrAuthor(bookSearchRequest.getIsbn(), bookSearchRequest.getAuthor());
    }

    public void createBook(Book book) {
        bookRepository.save(book);
    }
}
