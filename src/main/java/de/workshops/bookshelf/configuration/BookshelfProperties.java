package de.workshops.bookshelf.configuration;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import java.net.URL;

@Validated
@ConfigurationProperties(prefix = "bookshelf")
@Getter
@Setter
public class BookshelfProperties {

    @NotBlank
    private String owner;

    @Min(1)
    private int capacity;

    @NestedConfigurationProperty
    private IsbnLookupProperties isbnLookup = new IsbnLookupProperties();

    @Getter
    @Setter
    public static class IsbnLookupProperties {

        private URL url;

        private String apiKey;

    }
}
