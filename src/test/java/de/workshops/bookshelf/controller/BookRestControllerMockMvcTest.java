package de.workshops.bookshelf.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookRestControllerMockMvcTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BookRepository bookRepository;

    @Test
    @WithMockUser
    void GET_book_should_return_all_books() throws Exception {
        List<Book> books = bookRepository.findAll();

        mvc.perform(get("/book"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(books.size())))
                .andExpect(jsonPath("$[1].title", is(books.get(1).getTitle())));
    }

    @Test
    @WithMockUser
    void GET_book_should_return_at_least_one_Clean_Code_book() throws Exception {
        MvcResult result = mvc.perform(get("/book")).andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<Book> books = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        assertThat(books).anySatisfy(book -> {
            assertThat(book.getTitle()).isEqualTo("Clean Code");
        });
    }

    @TestConfiguration
    static class JacksonTestConfiguration {

        @Bean
        public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
            return builder -> builder.featuresToEnable(INDENT_OUTPUT);
        }
    }
}
