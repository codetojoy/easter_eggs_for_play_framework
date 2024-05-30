package app.tasks;

import models.Book;
import services.BookService;

import com.google.inject.Inject;
import java.util.List;

public class SimpleBookServiceTask implements Task {
    private final BookService bookService;

    @Inject
    public SimpleBookServiceTask(BookService bookService) {
        this.bookService = bookService;
    }
    
    @Override
    public boolean run() {
        emitIdentity();

        List<Book> books = bookService.getBooks();

        for (Book book : books) {
            System.out.println("TRACER book: " + book.toString());
        }

        return true;
    }
}
