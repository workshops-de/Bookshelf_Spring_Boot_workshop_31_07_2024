package de.workshops.bookshelf.controller;

import de.workshops.bookshelf.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class BookRestControllerRestClientTest {

    RestClient restClient;

    @BeforeEach
    void setUp(
            @Autowired RestClient.Builder restClientBuilder,
            @LocalServerPort int port
    ) {
        restClient = restClientBuilder.baseUrl("http://localhost:" + port).build();
    }

    @Test
    void GET_book_should_return_all_books() {
        ResponseEntity<List<Book>> response = restClient
                .get().uri("/book")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Book> books = response.getBody();
        assertThat(books).hasSize(3);
    }
}
