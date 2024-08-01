package de.workshops.bookshelf.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.workshops.bookshelf.domain.Book;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepository {

    private final List<Book> books;

    public BookRepository(ObjectMapper mapper, ResourceLoader resourceLoader) throws Exception {
        Resource resource = resourceLoader.getResource("classpath:books.json");
        this.books = mapper.readValue(resource.getInputStream(), new TypeReference<>() {
        });
    }

    public List<Book> getAllBooks() {
        return books;
    }
}
