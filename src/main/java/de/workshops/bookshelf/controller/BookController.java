package de.workshops.bookshelf.controller;

import de.workshops.bookshelf.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getBookOverview(Model model) {
        model.addAttribute("books", bookService.getAllBooks());

        return "book_overview";
    }
}
