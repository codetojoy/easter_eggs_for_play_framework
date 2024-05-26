package app.tasks;

import models.*;
import services.*;

import java.util.*;

import javax.inject.Inject;

public class SimpleTask implements Task {
    private final BookService bookService;
    
    @Inject
    public SimpleTask(BookService bookService) {
        System.out.println("TRACER hello from SimpleTask ctor");
        this.bookService = bookService;
    }

    @Override
    public boolean run() {
        List<Book> books = bookService.getBooks();

        for (Book book : books) {
            System.out.println("TRACER book: " + book.toString());
        }

        return true;
    }
}
