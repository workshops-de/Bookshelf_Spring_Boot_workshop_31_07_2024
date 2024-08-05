package de.workshops.bookshelf.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SwaggerConfigurationTest {

    @Nested
    class DuringDevelopment {

        @Test
        void Swagger_is_available(@Autowired MockMvc mvc) throws Exception {
            mvc.perform(get("/swagger-ui/index.html"))
                    .andExpect(status().isOk());
        }

        @Test
        void OpenAPI_Info_is_available(@Autowired OpenAPI openAPI) {
            assertThat(openAPI.getInfo().getTitle()).isEqualTo("Bookshelf API");
        }
    }

    @Nested
    @ActiveProfiles("prod")
    class InProduction {

        @Test
        void Swagger_is_not_available(@Autowired MockMvc mvc) throws Exception {
            mvc.perform(get("/swagger-ui/index.html"))
                    .andExpect(status().isNotFound());
        }

        @Test
        void OpenAPI_Info_is_not_available(@Autowired(required = false) OpenAPI openAPI) {
            assertThat(openAPI).isNull();
        }
    }
}
