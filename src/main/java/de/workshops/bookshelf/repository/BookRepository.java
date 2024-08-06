package de.workshops.bookshelf.repository;

import de.workshops.bookshelf.domain.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends ListCrudRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findByAuthor(String author);

    @Query("SELECT b FROM Book b WHERE (:isbn is null OR b.isbn = :isbn) AND (:author is null OR b.author LIKE '%' || :author || '%')")
    List<Book> findByIsbnOrAuthor(String isbn, String author);

}
