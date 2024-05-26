package controllers;

import models.*;
import services.*;

import io.ebean.DB;

import javax.inject.Inject;
import jakarta.persistence.PersistenceException;
import java.util.*;

import play.mvc.*;

public class BookController extends Controller {
    private BookService bookService;

    @Inject
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // This is not the "Play ethos" but we just want to test Ebean.
    // (In Play, we want to be async and query the DB on a different thread.)
    public Result list(Http.Request request) {
        List<Book> books = bookService.getBooks();
        return ok(views.html.book.render(books));
    }
}
