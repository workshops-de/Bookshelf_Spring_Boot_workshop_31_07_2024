package de.workshops.bookshelf.configuration;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ServerPropertiesTest {

    @Nested
    class ByDefault {

        @Value("${server.port:8080}")
        int port;

        @Test
        void Port_is_8080() {
            assertThat(port).isEqualTo(8080);
        }
    }

    @Nested
    @ActiveProfiles("prod")
    class InProduction {

        @Value("${server.port:8080}")
        int port;

        @Test
        void Port_is_8090() {
            assertThat(port).isEqualTo(8090);
        }
    }
}
